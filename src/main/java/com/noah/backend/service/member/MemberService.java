package com.noah.backend.service.member;

import com.noah.backend.domain.entity.Member;

public interface MemberService {

    public void registrationMember(Member member);

    public boolean isDuplicatedEmail(String email);
}
