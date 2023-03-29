package com.hanghae.sosohandiary.domain.mypage.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MyPageProfileResponseDto {

    private String nickname;
    private String statusMessage;

    @Builder
    public MyPageProfileResponseDto(String nickname, String statusMessage) {
        this.nickname = nickname;
        this.statusMessage = statusMessage;
    }

    public static MyPageProfileResponseDto of(String nickname, String statusMessage) {
        return MyPageProfileResponseDto.builder()
                .nickname(nickname)
                .statusMessage(statusMessage)
                .build();
    }

}
