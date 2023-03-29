package com.hanghae.sosohandiary.domain.mypage.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MyPageDiaryResponseDto {

    private final Long myDiaryCount;

    @Builder
    public MyPageDiaryResponseDto(Long myDiaryCount) {
        this.myDiaryCount = myDiaryCount;
    }

    public static MyPageDiaryResponseDto from(Long myDiaryCount) {
        return MyPageDiaryResponseDto.builder()
                .myDiaryCount(myDiaryCount)
                .build();
    }
}
