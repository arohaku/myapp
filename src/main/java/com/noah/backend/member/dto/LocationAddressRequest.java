package com.noah.backend.member.dto;

import com.noah.backend.post.domain.entity.Address;
import com.noah.backend.post.domain.entity.Location;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class LocationAddressRequest {

    private  String state;
    private  String city;
    private  String town;

    private  Double longitude;
    private  Double latitude;

    public Address toAddress() {
        return Address.builder()
                .state(state)
                .city(city)
                .town(town)
                .build();
    }

    public Location toLocation() {
        return Location.builder()
                .longitude(longitude)
                .latitude(latitude)
                .build();
    }
}
