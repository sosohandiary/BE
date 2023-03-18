package com.hanghae.sosohandiary.domain.mypage.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MypageFriendResponseDto {
    private final Long myFriendCount;

    @Builder
    public MypageFriendResponseDto(Long myFriendCount) {
        this.myFriendCount = myFriendCount;
    }

    public static MypageFriendResponseDto from(Long friendCount) {
        return MypageFriendResponseDto.builder()
                .myFriendCount(friendCount)
                .build();
    }
}
