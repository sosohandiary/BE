package com.hanghae.sosohandiary.domain.invite.dto;

import com.hanghae.sosohandiary.domain.invite.entity.Invite;
import com.hanghae.sosohandiary.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class InviteResponseDto {

    private Long inviteId;
    private Long diaryId;
    private String nickname;
    private boolean alarm;

    @Builder
    private InviteResponseDto(Long diaryId, Invite invite, Member member) {
        this.diaryId = diaryId;
        inviteId = invite.getId();
        nickname = member.getNickname();
        alarm = invite.isAlarm();
    }

    public static InviteResponseDto of(Long diaryId, Invite invite, Member member) {
        return InviteResponseDto.builder()
                .diaryId(diaryId)
                .invite(invite)
                .member(member)
                .build();
    }

}
