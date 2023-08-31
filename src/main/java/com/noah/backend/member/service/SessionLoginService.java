package com.noah.backend.member.service;

import com.noah.backend.member.domain.entity.Member;
import com.noah.backend.member.service.LoginService;
import com.noah.backend.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionLoginService implements LoginService {

    private final HttpSession httpSession;
    private final MemberService memberService;
    public static final String MEMBER_ID = "MEMBER_ID";

    @Override
    public void login(long id) {
        httpSession.setAttribute(MEMBER_ID, id);
    }


    @Override
    public void logout() {
        httpSession.removeAttribute(MEMBER_ID);
    }

    @Override
    public Member getLoginMember() {
        Long memberId = (Long) httpSession.getAttribute(MEMBER_ID);

        return memberService.findMemberById(memberId);
    }

    @Override
    public Long getLoginMemberId() {
        return (Long) httpSession.getAttribute(MEMBER_ID);
    }
}
