package com.noah.backend.controller;

import com.noah.backend.domain.dto.MemberDto;
import com.noah.backend.domain.entity.Member;
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

    /**
     * 사용자 로그인 기능
     * @param memberDto
     * @param httpSession
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<HttpStatus> login(@RequestBody @Valid MemberDto memberDto, HttpSession httpSession) {

        Member member = memberService.findMemberByEmail(memberDto.getEmail());

        if (passwordEncoder.matches(memberDto.getPassword(), member.getPassword())) {
            httpSession.setAttribute(MEMBER_ID, member.getId());
            return RESPONSE_OK;
        }
        return RESPONSE_BAD_REQUEST;
    }

    /**
     * 사용자 회원가입 기능
     * @param memberDto
     * @return
     */
    @PostMapping
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid MemberDto memberDto) {
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
}