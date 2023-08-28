package com.noah.backend.service.member;

import com.noah.backend.domain.entity.Member;

public interface LoginService {
    public void login(long id);

    public void logout();

    public Member getLoginMember();
}
