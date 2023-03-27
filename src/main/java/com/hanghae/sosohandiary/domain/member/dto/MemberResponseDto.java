package com.hanghae.sosohandiary.domain.member.dto;

import com.hanghae.sosohandiary.domain.friend.entity.Friend;
import com.hanghae.sosohandiary.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberResponseDto {

    private final Long memberId;
    private final Long friendListId;
    private final String name;
    private final String nickname;
    private final String statusMessage;

    @Builder
    public MemberResponseDto(Long id, Long friendListId, String name, String nickname, String statusMessage) {
        this.memberId = id;
        this.friendListId = friendListId;
        this.name = name;
        this.nickname = nickname;
        this.statusMessage = statusMessage;
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
                .build();
    }

}
