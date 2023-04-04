package com.hanghae.sosohandiary.domain.friend.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FriendResponseDto {

    private Long friendListId;
    private String friendNickName;
    private String nickname;
    private String profileImageUrl;
    private boolean alarm;

    @Builder
    private FriendResponseDto(Long id, String friendNickName, String nickname, String profileImageUrl, boolean alarm) {
        this.friendListId = id;
        this.nickname = nickname;
        this.friendNickName = friendNickName;
        this.profileImageUrl = profileImageUrl;
        this.alarm = alarm;
    }

    public static FriendResponseDto of(Long id, String friendNickName, String nickname, String profileImageUrl) {
        return FriendResponseDto.builder()
                .id(id)
                .friendNickName(friendNickName)
                .nickname(nickname)
                .profileImageUrl(profileImageUrl)
                .build();
    }

    public static FriendResponseDto of(Long id, String friendNickName, String nickname, boolean alarm) {
        return FriendResponseDto.builder()
                .id(id)
                .friendNickName(friendNickName)
                .nickname(nickname)
                .alarm(alarm)
                .build();
    }

}
