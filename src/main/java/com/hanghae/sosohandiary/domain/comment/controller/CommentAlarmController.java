package com.hanghae.sosohandiary.domain.comment.controller;

import com.hanghae.sosohandiary.domain.comment.dto.CommentAlarmResponseDto;
import com.hanghae.sosohandiary.domain.comment.service.CommentAlarmService;
import com.hanghae.sosohandiary.security.MemberDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment/alarm")
public class CommentAlarmController {

    private final CommentAlarmService commentAlarmService;

    @GetMapping
    public List<CommentAlarmResponseDto> alarmComment(@AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return commentAlarmService.alarmComment(memberDetails.getMember());
    }

    @PatchMapping("/{comment-id}")
    public CommentAlarmResponseDto readAlarm(@PathVariable("comment-id") Long commentId,
                                             @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return commentAlarmService.readAlarm(commentId, memberDetails.getMember());
    }
}
