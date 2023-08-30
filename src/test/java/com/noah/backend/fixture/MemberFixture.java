package com.noah.backend.fixture;

import com.noah.backend.member.dto.MemberDto;
import com.noah.backend.member.domain.entity.Member;

public class MemberFixture {

    public static final Member ADMIN_MEMBER = new Member(
            "admin@myapp.com",
            "Admin",
            "admin"
    );

    public static final Member MEMBER1 = new Member(
            "member1@myapp.com",
            "!Member123",
            "user1"
    );

    public static final Member MEMBER2 = new Member(
            "member2@myapp.com",
            "!Member123",
            "user2"
    );

    public static final MemberDto MEMBER_REGISTRATION_REQUEST = new MemberDto(
            "newmember@myapp.com",
            "!Newmember123",
            "newMember"
    );

    public static final Member NEW_MEMBER = new Member(
            "newmember@myapp.com",
            "!Newmember123",
            "newMember"
    );

    public static final Long MEMBER_UNIQUE_ID = 1L;

    public static final String UNIQUE_MEMBER_EMAIL = "unique@myapp.com";

    public static final String DUPLICATED_MEMBER_EMAIL = "duplicated@myapp.com";
}
