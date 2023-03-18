package com.hanghae.sosohandiary.domain.friend.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FriendResponseDto {

    private String nickName;

    @Builder
    private FriendResponseDto(String nickName) {
        this.nickName = nickName;
    }

    public static FriendResponseDto from(String nickName) {
        return FriendResponseDto.builder()
                .nickName(nickName)
                .build();
    }

}
