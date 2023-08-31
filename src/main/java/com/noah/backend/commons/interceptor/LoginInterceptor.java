package com.noah.backend.commons.interceptor;

import com.noah.backend.commons.annotation.LoginRequired;
import com.noah.backend.member.exception.UnAuthenticatedAccessException;
import com.noah.backend.member.exception.UnAuthorizedAccessException;
import com.noah.backend.member.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

    private final LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod && ((HandlerMethod) handler).hasMethodAnnotation(LoginRequired.class)) {
            Long memberId = loginService.getLoginMemberId();

            if(memberId == null) {
                throw new UnAuthenticatedAccessException();
            }
        }

        return true;
    }
}