package com.hanghae.sosohandiary.domain.mypage.dto;

import com.hanghae.sosohandiary.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MyPageProfileResponseDto {

    private Long memberId;
    private String profileImageUrl;
    private String nickname;
    private String statusMessage;


    @Builder
    private MyPageProfileResponseDto(Member member) {
        this.memberId = member.getId();
        this.profileImageUrl = member.getProfileImageUrl();
        this.nickname = member.getNickname();
        this.statusMessage = member.getStatusMessage();
    }

    public static MyPageProfileResponseDto of(Member member) {
        return MyPageProfileResponseDto.builder()
                .member(member)
                .build();
    }

}
