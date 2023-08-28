package com.noah.backend.service.member;

import com.noah.backend.commons.MemberNotFoundException;
import com.noah.backend.domain.dto.MemberDto;
import com.noah.backend.domain.entity.Member;
import com.noah.backend.domain.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final LoginService loginService;

    @Override
    @Transactional
    public void registrationMember(Member member) {
        memberRepository.save(member);
    }

    @Override
    public boolean isDuplicatedEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Override
    public Member findMemberByEmail(String email) {
        return memberRepository.findMemberByEmail(email).orElseThrow(MemberNotFoundException::new);
    }

    @Override
    public boolean isValidMember(MemberDto memberDto, PasswordEncoder passwordEncoder) {
        Member member = findMemberByEmail(memberDto.getEmail());

        if (passwordEncoder.matches(memberDto.getPassword(), member.getPassword())) {
            loginService.login(member.getEmail());
            return true;
        }
        return false;
    }
}