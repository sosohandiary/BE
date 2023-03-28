package com.hanghae.sosohandiary.domain.member.dto;

import com.hanghae.sosohandiary.domain.friend.entity.Enum.StatusFriend;
import com.hanghae.sosohandiary.domain.friend.entity.Friend;
import com.hanghae.sosohandiary.domain.member.entity.Member;
import jdk.jshell.Snippet;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberResponseDto {

    private final Long memberId;
    private final Long friendListId;
    private final String name;
    private final String nickname;
    private final String statusMessage;
    private final StatusFriend friendStatus;

    @Builder
    public MemberResponseDto(Long id, Long friendListId, String name, String nickname, String statusMessage, StatusFriend friendStatus) {
        this.memberId = id;
        this.friendListId = friendListId;
        this.name = name;
        this.nickname = nickname;
        this.statusMessage = statusMessage;
        this.friendStatus = friendStatus;
    }

    public static MemberResponseDto from(Member member) {
        return MemberResponseDto.builder()
                .id(member.getId())
                .name(member.getName())
                .build();
    }

    public static MemberResponseDto from(Friend friend) {
        return MemberResponseDto.builder()
                .id(friend.getFriend().getId())
                .friendListId(friend.getId())
                .name(friend.getFriend().getName())
                .nickname(friend.getFriend().getNickname())
                .statusMessage(friend.getFriend().getStatusMessage())
                .friendStatus(friend.getStatus())
                .build();
    }

    public static MemberResponseDto from(Member member, StatusFriend friendStatus) {
        return MemberResponseDto.builder()
                .id(member.getId())
                .name(member.getName())
                .nickname(member.getNickname())
                .statusMessage(member.getStatusMessage())
                .friendStatus(friendStatus)
                .build();
    }



}
