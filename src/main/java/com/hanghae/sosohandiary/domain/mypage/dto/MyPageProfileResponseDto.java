package com.hanghae.sosohandiary.domain.mypage.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MyPageProfileResponseDto {

    private String profileImageUrl;
    private String nickname;
    private String statusMessage;

    @Builder
    private MyPageProfileResponseDto(String profileImageUrl, String nickname, String statusMessage) {
        this.profileImageUrl = profileImageUrl;
        this.nickname = nickname;
        this.statusMessage = statusMessage;
    }

    public static MyPageProfileResponseDto of(String profileImageUrl, String nickname, String statusMessage) {
        return MyPageProfileResponseDto.builder()
                .profileImageUrl(profileImageUrl)
                .nickname(nickname)
                .statusMessage(statusMessage)
                .build();
    }

}
