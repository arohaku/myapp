package com.noah.backend.commons.config;

import com.noah.backend.commons.util.RoutingDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;

import static com.noah.backend.commons.util.DatabaseType.MASTER;
import static com.noah.backend.commons.util.DatabaseType.SLAVE;

@Configuration
@EnableTransactionManagement
@RequiredArgsConstructor
@ComponentScan(basePackages = {"com.noah.backend"})
public class DataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "datasource.master")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "datasource.slave")
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public DataSource routingDataSource() {
        RoutingDataSource routingDataSource = new RoutingDataSource();

        HashMap<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(MASTER, masterDataSource());
        dataSourceMap.put(SLAVE, slaveDataSource());

        routingDataSource.setDefaultTargetDataSource(masterDataSource());
        routingDataSource.setTargetDataSources(dataSourceMap);

        return routingDataSource;
    }

    @Primary
    @Bean
    @DependsOn({"masterDataSource", "slaveDataSource", "routingDataSource"})
    public DataSource dataSource() {
        return new LazyConnectionDataSourceProxy(routingDataSource());
    }
}