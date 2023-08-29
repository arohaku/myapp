package com.noah.backend.service.member;

import com.noah.backend.domain.dto.LocationAddressRequest;
import com.noah.backend.domain.dto.MemberDto;
import com.noah.backend.domain.dto.PasswordRequest;
import com.noah.backend.domain.dto.ProfileRequest;
import com.noah.backend.domain.entity.Member;
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
