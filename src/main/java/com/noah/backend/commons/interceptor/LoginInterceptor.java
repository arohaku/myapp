package com.noah.backend.commons.interceptor;

import com.noah.backend.commons.annotation.LoginRequired;
import com.noah.backend.member.exception.UnAuthenticatedAccessException;
import com.noah.backend.member.exception.UnAuthorizedAccessException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private static final String MEMBER_ID = "MEMBER_ID";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HandlerMethod handlerMethod = (HandlerMethod) handler;
//        String memberId = (String) request.getSession().getAttribute(MEMBER_ID);
        Long memberId = (Long) request.getSession().getAttribute(MEMBER_ID);

        if (handlerMethod.hasMethodAnnotation(LoginRequired.class) && memberId == null) {
            throw new UnAuthenticatedAccessException();
        }
        return true;
    }
}