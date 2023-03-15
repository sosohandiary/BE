package com.hanghae.sosohandiary.domain.member.repository;

import com.hanghae.sosohandiary.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByName(String name);
    Optional<Member> findByKakaoId(Long kakaoId);
    Optional<Member> findByEmail(String email);
}
