package com.hanghae.sosohandiary.domain.mypage.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MypageProfileResponseDto {

    private String nickname;
    private String statusMessage;

    @Builder
    public MypageProfileResponseDto(String nickname, String statusMessage) {
        this.nickname = nickname;
        this.statusMessage = statusMessage;
    }

    public static MypageProfileResponseDto of(String nickname, String statusMessage) {
        return MypageProfileResponseDto.builder()
                .nickname(nickname)
                .statusMessage(statusMessage)
                .build();
    }

}
