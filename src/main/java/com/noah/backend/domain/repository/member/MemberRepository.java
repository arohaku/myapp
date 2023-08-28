package com.noah.backend.domain.repository.member;

import com.noah.backend.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    public boolean existsByEmail(String email);
}
