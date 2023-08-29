package com.noah.backend.service.member;

import com.noah.backend.commons.exception.MemberNotFoundException;
import com.noah.backend.domain.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    private SessionLoginService loginService;

    @Mock
    private MemberServiceImpl memberServiceImpl;

    private MockHttpSession mockHttpSession;

    private static final String MEMBER_ID = "MEMBER_ID";

    private static final Long LOGIN_MEMBER_ID = 1L;

    private Member member;

    @BeforeEach
    void setUp() {

        member = Member.builder()
                .email("noah@admin.com")
                .password("1q2w3e4r!")
                .nickname("하쿠애비")
                .build();


        mockHttpSession = new MockHttpSession();

        loginService = new SessionLoginService(mockHttpSession, memberServiceImpl);
    }

    @Test
    @DisplayName("사용자가 로그인 성공하는 경우 세션에 사용자 아이디를 저장한다.")
    void successToLogin() {
        // when
        loginService.login(LOGIN_MEMBER_ID);

        // then
        assertThat(mockHttpSession.getAttribute(MEMBER_ID)).isNotNull();
        assertThat(mockHttpSession.getAttribute(MEMBER_ID)).isEqualTo(1L);
    }

    @Test
    @DisplayName("사용자가 로그아웃에 성공하는 경우 세션에 저장된 사용자 아이디를 제거한다.")
    void successToLogout() {
        // given
        mockHttpSession.setAttribute(MEMBER_ID, LOGIN_MEMBER_ID);

        // when
        loginService.logout();

        // then
        assertThat(mockHttpSession.getAttribute("MEMBER_ID")).isNull();
    }

    @Test
    @DisplayName("사용자가 로그인된 상태이면 세션에 저장된 사용자 아이디를 통해 사용자를 조회한다.")
    void isExistLoginMember() {
        // given
        mockHttpSession.setAttribute(MEMBER_ID, LOGIN_MEMBER_ID);
        when(memberServiceImpl.findMemberById(anyLong())).thenReturn(member);

        // when
        Member loginMember = loginService.getLoginMember();

        // then
        assertThat(loginMember).isNotNull();
    }

    @Test
    @DisplayName("로그인 사용자 조회 실패 테스트")
    void isNotExistLoginMember() {
        // given
        mockHttpSession.setAttribute(MEMBER_ID, 2L);
        when(memberServiceImpl.findMemberById(anyLong())).thenThrow(MemberNotFoundException.class);

        // then
        assertThrows(MemberNotFoundException.class, () -> {
            loginService.getLoginMember();
        });
    }

}