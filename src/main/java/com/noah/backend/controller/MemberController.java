package com.noah.backend.controller;

import com.noah.backend.commons.annotation.LoginRequired;
import com.noah.backend.domain.dto.*;
import com.noah.backend.domain.entity.Member;
import com.noah.backend.interceptor.LoginMember;
import com.noah.backend.service.member.LoginService;
import com.noah.backend.service.member.MemberService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import static com.noah.backend.commons.HttpStatusResponseEntity.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/members")
public class MemberController {

    private static final String MEMBER_ID = "MEMBER_ID";
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final LoginService loginService;

    /**
     * 사용자 로그인 기능
     * @param memberDto
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<HttpStatus> login(@RequestBody @Valid MemberDto memberDto) {

        boolean isValidMember = memberService.isValidMember(memberDto, passwordEncoder);

        if (isValidMember) {
            loginService.login(memberService.findMemberByEmail(memberDto.getEmail()).getId());
            return RESPONSE_OK;
        }
        return RESPONSE_BAD_REQUEST;
    }

    /**
     * 사용자 로그아웃 기능
     * LoginRequired를 통해 로그인 여부 체크
     * @return
     */
    @LoginRequired
    @GetMapping("/logout")
    public ResponseEntity<HttpStatus> logout() {
        loginService.logout();
        return RESPONSE_OK;
    }

    /**
     * 사용자 회원가입 기능
     * @param memberDto
     * @return
     */
    @PostMapping
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid MemberDto memberDto) {

        // 클라이언트에서 사용자 이메일 중복체크를 수행하지만 API요청에 의한 예외상황에 대비하여 더블체크
        boolean isDuplicated = memberService.isDuplicatedEmail(memberDto.getEmail());

        if(isDuplicated) {
            return RESPONSE_CONFLICT;
        }
        Member member = MemberDto.toEntity(memberDto, passwordEncoder);
        memberService.registrationMember(member);
        return RESPONSE_OK;
    }


    /**
     * 사용자 이메일 중복체크 기능
     * @param email
     * @return
     */
    @GetMapping("/duplicated/{email}")
    public ResponseEntity<HttpStatus> isDuplicatedEmail(@PathVariable String email) {
        boolean isDuplicated = memberService.isDuplicatedEmail(email);

        if(isDuplicated) {
            return RESPONSE_CONFLICT;
        }

        return RESPONSE_OK;
    }

    /**
     * 사용자 프로필 조회 기능
     * @return
     */
    @LoginRequired
    @GetMapping("/my-profile")
    public ResponseEntity<ProfileResponse> getMemberProfile(@LoginMember Member member) {

        return ResponseEntity.ok(ProfileResponse.of(member));
    }


    /**
     * 사용자 프로필 정보 업데이트 기능
     * @param profileRequest
     * @return
     */
    @LoginRequired
    @PutMapping("/my-profile")
    public ResponseEntity<ProfileResponse> updateMemberProfile(@LoginMember Member member, @RequestBody ProfileRequest profileRequest) {

        memberService.updateMemberProfile(member, profileRequest);

        return ResponseEntity.ok(ProfileResponse.of(member));
    }

    @LoginRequired
    @PutMapping("/password")
    public ResponseEntity<HttpStatus> changePassword(@LoginMember Member member,  @Valid @RequestBody PasswordRequest passwordRequest) {

        if(memberService.isValidPassword(member, passwordRequest, passwordEncoder)) {
            memberService.updateMemberPassword(member, passwordRequest, passwordEncoder);
        }

        return RESPONSE_OK;
    }

    @LoginRequired
    @PutMapping("/my-location")
    public ResponseEntity<HttpStatus> setMemberLocationAddress(@LoginMember Member member, @RequestBody LocationAddressRequest locationAddressRequest) {

        memberService.setMemberLocationAddress(member, locationAddressRequest);

        return RESPONSE_OK;
    }

}