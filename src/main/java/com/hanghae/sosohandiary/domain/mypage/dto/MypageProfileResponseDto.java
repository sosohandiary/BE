package com.hanghae.sosohandiary.domain.mypage.dto;

import com.hanghae.sosohandiary.domain.member.entity.Gender;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MypageProfileResponseDto {

    private String nickname;
    private Gender gender;
    private String statusMessage;

    @Builder
    public MypageProfileResponseDto(String nickname, Gender gender, String statusMessage) {
        this.nickname = nickname;
        this.gender = gender;
        this.statusMessage = statusMessage;
    }

    public static MypageProfileResponseDto of(String nickname, Gender gender, String statusMessage) {
        return MypageProfileResponseDto.builder()
                .nickname(nickname)
                .gender(gender)
                .statusMessage(statusMessage)
                .build();
    }

}
