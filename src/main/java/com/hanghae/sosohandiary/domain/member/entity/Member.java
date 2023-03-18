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

    @Column(nullable = false, length = 100)
    private String password;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 10, nullable = false)
    private String nickname;

    @Column(length = 10, nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Column(length = 100)
    private String statusMessage;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MemberRoleEnum role;

    @Builder
    public Member(Long kakaoId, String email, String password, String name, String nickname, Gender gender, String statusMessage, MemberRoleEnum role) {
        this.kakaoId = kakaoId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.gender = gender;
        this.statusMessage = statusMessage;
        this.role = role;
    }

    // 카카오 로그인
    public static Member of(String email, Long kakaoId, String password, String name, String nickname, Gender gender, MemberRoleEnum role) {
        return Member.builder()
                .email(email)
                .kakaoId(kakaoId)
                .password(password)
                .name(name)
                .nickname(nickname)
                .gender(gender)
                .role(role)
                .build();
    }

    // 로그인
    public static Member of(String email, String password, String name, String nickname, Gender gender, MemberRoleEnum role) {
        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .nickname(nickname)
                .gender(gender)
                .role(role)
                .build();
    }

    public Member kakaoIdUpdate(Long kakaoId) {
        this.kakaoId = kakaoId;
        return this;
    }

    public void updateProfile(String nickname, String statusMessage) {
        this.nickname = nickname;
        this.statusMessage = statusMessage;
    }

}
