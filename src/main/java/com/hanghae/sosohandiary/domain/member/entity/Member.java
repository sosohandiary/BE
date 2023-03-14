package com.hanghae.sosohandiary.domain.member.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long kakaoId;
    @Column(length = 30, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MemberRoleEnum role;

    @Builder
    public Member(String email, Long kakaoId, String password, String name, MemberRoleEnum role) {
        this.email = email;
        this.kakaoId = kakaoId;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    public static Member of(String email, Long kakaoId, String password, String name, MemberRoleEnum role) {
        return Member.builder()
                .email(email)
                .kakaoId(kakaoId)
                .password(password)
                .name(name)
                .role(role)
                .build();
    }

    public static Member of(String email, String password, String name, MemberRoleEnum role) {
        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .role(role)
                .build();
    }


    public Member kakaoIdUpdate(Long kakaoId) {
        this.kakaoId = kakaoId;
        return this;
    }
}
