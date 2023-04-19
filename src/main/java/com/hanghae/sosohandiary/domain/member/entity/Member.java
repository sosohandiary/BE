package com.hanghae.sosohandiary.domain.member.entity;

import com.hanghae.sosohandiary.domain.member.dto.JoinRequestDto;
import com.hanghae.sosohandiary.domain.member.dto.KakaoMemberInfoDto;
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
    @Column(length = 100)
    private String statusMessage;
    @Column
    private String profileImageUrl;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MemberRoleEnum role;

    @Builder
    private Member(Long kakaoId, String email, String password, String name, String nickname, String statusMessage, String profileImageUrl, MemberRoleEnum role) {
        this.kakaoId = kakaoId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.statusMessage = statusMessage;
        this.profileImageUrl = profileImageUrl;
        this.role = role;
    }

    // 카카오 로그인
    public static Member of(KakaoMemberInfoDto kakaoMemberInfoDto, String password, MemberRoleEnum role) {
        return Member.builder()
                .email(kakaoMemberInfoDto.getEmail())
                .kakaoId(kakaoMemberInfoDto.getId())
                .password(password)
                .name(kakaoMemberInfoDto.getName())
                .nickname(kakaoMemberInfoDto.getNickname())
                .role(role)
                .build();
    }

    // 로그인
    public static Member of(JoinRequestDto joinRequestDto, String password, MemberRoleEnum role) {
        return Member.builder()
                .email(joinRequestDto.getEmail())
                .password(password)
                .name(joinRequestDto.getName())
                .nickname(joinRequestDto.getNickname())
                .role(role)
                .build();
    }

    public Member kakaoIdUpdate(Long kakaoId) {
        this.kakaoId = kakaoId;
        return this;
    }

    public void updateProfile(String profileImageUrl, String nickname, String statusMessage) {
        this.profileImageUrl = profileImageUrl;
        this.nickname = nickname;
        this.statusMessage = statusMessage;
    }

    public void updateProfile(String nickname, String statusMessage) {
        this.nickname = nickname;
        this.statusMessage = statusMessage;
    }

}
