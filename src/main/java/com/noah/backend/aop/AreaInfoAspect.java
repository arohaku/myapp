package com.noah.backend.aop;

import com.noah.backend.commons.advice.exception.AreaInfoNotDefinedException;
import com.noah.backend.commons.advice.exception.MemberNotFoundException;
import com.noah.backend.domain.entity.Member;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class AreaInfoAspect {

    @Before("@annotation(com.noah.backend.commons.annotation.AreaInfoRequired)")
    public void isValidAreaInfo(JoinPoint joinPoint) {
        Member member = Arrays.stream(joinPoint.getArgs())
                .filter(Member.class::isInstance)
                .map(Member.class::cast)
                .findFirst()
                .orElseThrow(MemberNotFoundException::new);


        if(member.getAddress() == null || member.getLocation() == null) {
            throw new AreaInfoNotDefinedException("지역 정보를 등록해주세요.");
        }
    }
}