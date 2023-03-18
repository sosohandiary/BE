package com.hanghae.sosohandiary.domain.mypage.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MypageDiaryResponseDto {

    private final Long myDiaryCount;

    @Builder
    public MypageDiaryResponseDto(Long myDiaryCount) {
        this.myDiaryCount = myDiaryCount;
    }

    public static MypageDiaryResponseDto from(Long myDiaryCount) {
        return MypageDiaryResponseDto.builder()
                .myDiaryCount(myDiaryCount)
                .build();
    }
}
