package com.hanghae.sosohandiary.domain.comment.controller;

import com.hanghae.sosohandiary.domain.comment.dto.CommentAlarmResponseDto;
import com.hanghae.sosohandiary.domain.comment.dto.CommentRequestDto;
import com.hanghae.sosohandiary.domain.comment.dto.CommentResponseDto;
import com.hanghae.sosohandiary.domain.comment.service.CommentService;
import com.hanghae.sosohandiary.security.MemberDetailsImpl;
import com.hanghae.sosohandiary.utils.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/detail/{detail-id}")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comment")
    public CommentResponseDto createComment(@PathVariable("detail-id") Long id,
                                            @RequestBody CommentRequestDto requestDto,
                                            @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return commentService.createComment(id, requestDto, memberDetails.getMember());
    }

    @GetMapping("/comments")
    public List<CommentResponseDto> getComment(@PathVariable("detail-id") Long id) {
        return commentService.getComment(id);
    }

    @PatchMapping("/comment/{comment-id}")
    public CommentResponseDto updateComment(@PathVariable("detail-id") Long detailId,
                                            @PathVariable("comment-id") Long commentId,
                                            @RequestBody CommentRequestDto requestDto,
                                            @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return commentService.updateComment(detailId, commentId, requestDto, memberDetails.getMember());
    }

    @DeleteMapping("/comment/{comment-id}")
    public MessageDto deleteComment(@PathVariable("detail-id") Long detailId,
                                    @PathVariable("comment-id") Long commentId,
                                    @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return commentService.deleteComment(detailId, commentId, memberDetails.getMember());
    }

}
