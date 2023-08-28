package com.noah.backend.domain.repository.member;

import com.noah.backend.domain.entity.member.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    public boolean existsByEmail(String email);
}
