package com.hanghae.sosohandiary.domain.mypage.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MyPageFriendResponseDto {
    private final Long myFriendCount;

    @Builder
    public MyPageFriendResponseDto(Long myFriendCount) {
        this.myFriendCount = myFriendCount;
    }

    public static MyPageFriendResponseDto from(Long friendCount) {
        return MyPageFriendResponseDto.builder()
                .myFriendCount(friendCount)
                .build();
    }
}
