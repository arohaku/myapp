package com.noah.backend.member.dto;

import com.noah.backend.post.domain.entity.Address;
import com.noah.backend.post.domain.entity.Location;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class LocationAddressRequest {

    private final String state;
    private final String city;
    private final String town;

    private final Double longitude;
    private final Double latitude;

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
