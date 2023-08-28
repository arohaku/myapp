package com.noah.backend.domain.repository.member;

import com.noah.backend.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    public boolean existsByEmail(String email);

    //Optional...
    public Optional<Member> findMemberByEmail(String email);

    public Optional<Member> findMemberById(long id);
}
