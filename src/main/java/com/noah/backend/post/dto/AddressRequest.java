package com.noah.backend.post.dto;

import com.noah.backend.post.domain.entity.Address;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressRequest {

    @NotEmpty
    private String state;

    @NotEmpty
    private String city;

    @NotEmpty
    private String town;

    public AddressRequest(String state, String city, String town) {
        this.state = state;
        this.city = city;
        this.town = town;
    }

    public static Address toEntity(AddressRequest address) {
        return Address.builder()
                .state(address.getState())
                .city(address.getCity())
                .town(address.getTown())
                .build();
    }
}