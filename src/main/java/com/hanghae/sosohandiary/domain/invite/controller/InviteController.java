package com.hanghae.sosohandiary.domain.invite.controller;

import com.hanghae.sosohandiary.domain.invite.dto.InviteResponseDto;
import com.hanghae.sosohandiary.domain.invite.service.InviteService;
import com.hanghae.sosohandiary.security.MemberDetailsImpl;
import com.hanghae.sosohandiary.utils.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/invite")
public class InviteController {

    private final InviteService inviteService;

    // 공유 다이어리 초대
    @PostMapping("/{diary-id}/{to-member-id}")
    public MessageDto invite(@PathVariable(name = "diary-id") Long diaryId,
                             @AuthenticationPrincipal MemberDetailsImpl memberDetails,
                             @PathVariable(name = "to-member-id") Long toMemberId) {
        return inviteService.invite(diaryId, memberDetails.getMember(), toMemberId);
    }

    // 공유 다이어리 초대 거절
    @DeleteMapping("/{invite-id}")
    public MessageDto deny(@PathVariable("invite-id") Long inviteId) {
        return inviteService.deny(inviteId);
    }

    @GetMapping("/alarm")
    public List<InviteResponseDto> alarmInvite(@AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return inviteService.alarmInvite(memberDetails.getMember());
    }
}
