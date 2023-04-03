package com.hanghae.sosohandiary.domain.friend.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FriendResponseDto {

    private Long friendListId;
    private String friendNickName;
    private String nickname;
    private String profileImageUrl;

    @Builder
    private FriendResponseDto(Long id, String friendNickName, String nickname, String profileImageUrl) {
        this.friendListId = id;
        this.nickname = nickname;
        this.friendNickName = friendNickName;
        this.profileImageUrl = profileImageUrl;
    }

    public static FriendResponseDto of(Long id, String friendNickName, String nickname, String profileImageUrl) {
        return FriendResponseDto.builder()
                .id(id)
                .friendNickName(friendNickName)
                .nickname(nickname)
                .profileImageUrl(profileImageUrl)
                .build();
    }

}
