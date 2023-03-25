package com.hanghae.sosohandiary.domain.friend.dto;

import com.hanghae.sosohandiary.domain.friend.entity.FriendList;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FriendResponseDto {

    private Long friendListId;

    private String friendNickName;
    private String myNickname;

    @Builder
    private FriendResponseDto(FriendList friendList) {
        this.friendListId=friendList.getId();
        this.myNickname = friendList.getFriend().getNickname();
        this.friendNickName = friendList.getMember().getNickname();

    }

//    public static FriendResponseDto from(String nickName) {
//        return FriendResponseDto.builder()
//                .nickName(nickName)
//                .build();
//    }
    public static FriendResponseDto from(FriendList friendList) {
        return FriendResponseDto.builder()
                .friendList(friendList)
                .build();
    }

}
