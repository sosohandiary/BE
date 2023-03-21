package com.hanghae.sosohandiary.domain.member.dto;

import com.hanghae.sosohandiary.domain.friend.entity.FriendList;
import com.hanghae.sosohandiary.domain.member.entity.Gender;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberResponseDto {

    private final String name;
    private final String nickname;
    private final Gender gender;
    private final String statusMessage;

    @Builder
    public MemberResponseDto(String name, String nickname, Gender gender, String statusMessage) {
        this.name = name;
        this.nickname = nickname;
        this.gender = gender;
        this.statusMessage = statusMessage;
    }

    public static MemberResponseDto from(String name) {
        return MemberResponseDto.builder()
                .name(name)
                .build();
    }

    public static MemberResponseDto from(FriendList friend) {
        return MemberResponseDto.builder()
                .name(friend.getFriend().getName())
                .nickname(friend.getFriend().getNickname())
                .gender(friend.getFriend().getGender())
                .statusMessage(friend.getFriend().getStatusMessage())
                .build();
    }

}