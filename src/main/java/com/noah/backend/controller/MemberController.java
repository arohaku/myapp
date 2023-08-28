package com.noah.backend.controller;

import com.noah.backend.domain.entity.member.MemberEntity;
import com.noah.backend.service.member.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.noah.backend.commons.HttpStatusResponseEntity.RESPONSE_CONFLICT;
import static com.noah.backend.commons.HttpStatusResponseEntity.RESPONSE_OK;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    /**
     * 사용자 회원가입 기능
     * @param memberEntity
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid MemberEntity memberEntity) {
        memberService.registrationMember(memberEntity);
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