package com.hanghae.sosohandiary.domain.mypage.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MypageResponseDto {

    private String nickname;
    private String statusMessage;

    @Builder
    public MypageResponseDto(String nickname, String statusMessage) {
        this.nickname = nickname;
        this.statusMessage = statusMessage;
    }

    public static MypageResponseDto of(String nickname, String statusMessage) {
        return MypageResponseDto.builder()
                .nickname(nickname)
                .statusMessage(statusMessage)
                .build();
    }

}
