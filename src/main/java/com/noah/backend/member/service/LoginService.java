package com.noah.backend.member.service;

import com.noah.backend.member.domain.entity.Member;

public interface LoginService {
    public void login(long id);

    public void logout();

    public Member getLoginMember();
}
