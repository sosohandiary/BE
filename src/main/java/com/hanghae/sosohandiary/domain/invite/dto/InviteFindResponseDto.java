package com.hanghae.sosohandiary.domain.invite.dto;

import com.hanghae.sosohandiary.domain.invite.entity.Invite;
import com.hanghae.sosohandiary.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class InviteFindResponseDto {

    private Long memberId;
    private String nickname;
    private boolean alarm;

    @Builder
    private InviteFindResponseDto(Invite invite, Member member) {
        memberId = invite.getToMember().getId();
        nickname = member.getNickname();
        alarm = invite.isAlarm();
    }

    public static InviteFindResponseDto of(Invite invite, Member member) {
        return InviteFindResponseDto.builder()
                .invite(invite)
                .member(member)
                .build();
    }

}
