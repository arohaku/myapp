package com.noah.backend.member.domain.entity;

import com.noah.backend.member.dto.LocationAddressRequest;
import com.noah.backend.post.domain.entity.Address;
import com.noah.backend.post.domain.entity.Location;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name = "MEMBER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member{

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String email;

    private String password;

    private String nickname;

    @Embedded
    private Address address;

    @Embedded
    private Location location;

    @Builder
    public Member(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public void updateProfile(String nickname) {
        this.nickname = nickname;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void setMemberLocationAddress(LocationAddressRequest locationAddress) {
        this.address = locationAddress.toAddress();
        this.location = locationAddress.toLocation();
    }
}
