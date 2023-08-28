package com.noah.backend.service.member;

import com.noah.backend.domain.entity.Member;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionLoginService implements LoginService {

    private final HttpSession httpSession;
    private final MemberService memberService;
    private static final String MEMBER_ID = "MEMBER_ID";

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
}
