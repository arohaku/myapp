package com.noah.backend.service.member;

import com.noah.backend.domain.entity.member.MemberEntity;

public interface MemberService {

    public void registrationMember(MemberEntity memberEntity);

    public boolean isDuplicatedEmail(String email);
}
