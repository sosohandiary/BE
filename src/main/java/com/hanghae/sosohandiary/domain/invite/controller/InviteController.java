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

    @PostMapping("/{diary-id}/{to-member-id}")
    public MessageDto invite(@PathVariable("diary-id") Long diaryId,
                             @AuthenticationPrincipal MemberDetailsImpl memberDetails,
                             @PathVariable("to-member-id") Long toMemberId) {
        return inviteService.invite(diaryId, memberDetails.getMember(), toMemberId);
    }

    @DeleteMapping("/{invite-id}")
    public MessageDto deny(@PathVariable("invite-id") Long inviteId) {
        return inviteService.deny(inviteId);
    }

    @GetMapping("/alarm")
    public List<InviteResponseDto> alarmInvite(@AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return inviteService.alarmInvite(memberDetails.getMember());
    }

    @PatchMapping("/alarm/read/{invite-id}")
    public InviteResponseDto alarmRead(@PathVariable("invite-id") Long inviteId,
                                       @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return inviteService.alarmRead(inviteId, memberDetails.getMember());
    }

    @GetMapping("/{diary-id}/list")
    public List<InviteResponseDto> findInviteList(@PathVariable("diary-id") Long diaryId,
                                                  @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return inviteService.findInviteList(diaryId, memberDetails.getMember());
    }

}
