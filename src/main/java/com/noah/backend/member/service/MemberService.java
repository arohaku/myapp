package com.noah.backend.member.service;

import com.noah.backend.member.dto.LocationAddressRequest;
import com.noah.backend.member.dto.MemberDto;
import com.noah.backend.member.dto.PasswordRequest;
import com.noah.backend.member.dto.ProfileRequest;
import com.noah.backend.member.domain.entity.Member;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface MemberService {

    public void registrationMember(Member member);

    public boolean isDuplicatedEmail(String email);

    public Member findMemberByEmail(String email);

    public boolean isValidMember(MemberDto memberDto, PasswordEncoder passwordEncoder);

    public Member findMemberById(long id);

    public void updateMemberProfile(Member member, ProfileRequest profileRequest);

    public boolean isValidPassword(Member member, PasswordRequest passwordRequest, PasswordEncoder passwordEncoder);

    public void updateMemberPassword(Member member, PasswordRequest passwordRequest, PasswordEncoder passwordEncoder);

    public void setMemberLocationAddress(Member member, LocationAddressRequest locationAddressRequest);

}
