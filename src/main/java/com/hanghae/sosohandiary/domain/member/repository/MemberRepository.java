package com.hanghae.sosohandiary.domain.member.repository;

import com.hanghae.sosohandiary.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByKakaoId(Long kakaoId);

    Optional<Member> findByEmail(String email);

    List<Member> findByNameContaining(String name);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);
}
