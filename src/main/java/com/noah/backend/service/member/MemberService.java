package com.noah.backend.service.member;

import com.noah.backend.domain.dto.MemberDto;
import com.noah.backend.domain.entity.Member;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface MemberService {

    public void registrationMember(Member member);

    public boolean isDuplicatedEmail(String email);

    public Member findMemberByEmail(String email);

    public boolean isValidMember(MemberDto memberDto, PasswordEncoder passwordEncoder);
}
