package com.noah.backend.commons.util;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import static com.noah.backend.commons.util.DatabaseType.MASTER;
import static com.noah.backend.commons.util.DatabaseType.SLAVE;

//Transaction이 readOnly인지 여부에 따라서 데이터베이스를 선택할 수 있는 클래스 임.

public class RoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return TransactionSynchronizationManager.isCurrentTransactionReadOnly() ? SLAVE : MASTER;
    }
}