package com.hanghae.sosohandiary.domain.friend.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FriendResponseDto {

    private Long friendListId;
    private String friendNickName;
    private String nickname;

    @Builder
    private FriendResponseDto(Long id, String friendNickName, String nickname) {
        this.friendListId = id;
        this.nickname = nickname;
        this.friendNickName = friendNickName;
    }

    public static FriendResponseDto of(Long id, String friendNickName, String nickname) {
        return FriendResponseDto.builder()
                .id(id)
                .friendNickName(friendNickName)
                .nickname(nickname)
                .build();
    }

}
