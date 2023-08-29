package com.noah.backend.service.member;

import com.noah.backend.commons.exception.MemberNotFoundException;
import com.noah.backend.domain.dto.LocationAddressRequest;
import com.noah.backend.domain.dto.MemberDto;
import com.noah.backend.domain.dto.PasswordRequest;
import com.noah.backend.domain.dto.ProfileRequest;
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

    @Override
    public Member findMemberById(long id) {
        return memberRepository.findMemberById(id).orElseThrow(MemberNotFoundException::new);
    }

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
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void updateMemberProfile(Member member, ProfileRequest profileRequest) {
        member.updateProfile(profileRequest.getNickname());
    }

    @Override
    public boolean isValidPassword(Member member, PasswordRequest passwordRequest, PasswordEncoder passwordEncoder) {

        if(passwordEncoder.matches(passwordRequest.getOldPassword(), member.getPassword())) {
            return true;
        }

        return false;
    }

    @Override
    @Transactional
    public void updateMemberPassword(Member member, PasswordRequest passwordRequest, PasswordEncoder passwordEncoder) {
        member.updatePassword(passwordEncoder.encode(passwordRequest.getNewPassword()));
    }

    @Override
    @Transactional
    public void setMemberLocationAddress(Member member, LocationAddressRequest locationAddressRequest) {
        member.setMemberLocationAddress(locationAddressRequest);
    }

}