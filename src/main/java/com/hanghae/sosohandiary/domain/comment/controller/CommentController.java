package com.hanghae.sosohandiary.domain.comment.controller;

import com.hanghae.sosohandiary.domain.comment.dto.CommentResponseDto;
import com.hanghae.sosohandiary.domain.comment.service.CommentService;
import com.hanghae.sosohandiary.domain.comment.dto.CommentRequestDto;
import com.hanghae.sosohandiary.domain.comment.entity.Comment;
import com.hanghae.sosohandiary.security.MemberDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/detail/{detail-id}/comment")
    public CommentResponseDto createComment(@PathVariable(name = "detail-id")Long id,
                                 @RequestBody CommentRequestDto requestDto,
                                 @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return commentService.createComment(id, requestDto, memberDetails.getMember());
    }

    @PatchMapping("/detail/{detail-id}/comment/{comment-id}")
    public CommentResponseDto updateComment(@PathVariable(name = "detail-id") Long detailId,
                                            @PathVariable(name = "comment-id") Long commentId,
                                            @RequestBody CommentRequestDto requestDto,
                                            @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return commentService.updateComment(detailId, commentId, requestDto, memberDetails.getMember());
    }


}
