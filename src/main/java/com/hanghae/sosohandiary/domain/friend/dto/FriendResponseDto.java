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

    //    public static FriendResponseDto from(String nickName) {
//        return FriendResponseDto.builder()
//                .nickName(nickName)
//                .build();
//    }
//    public static FriendResponseDto from(FriendList friendList) {
//        return FriendResponseDto.builder()
//                .id(friendList.getId())
//                .friendNickName(friendList.getFriend().getNickname())
//                .myNickname(friendList.getMember().getNickname())
//                .build();
//    }

}
