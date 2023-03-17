package com.hanghae.sosohandiary.domain.comment.controller;

import com.hanghae.sosohandiary.domain.comment.dto.CommentResponseDto;
import com.hanghae.sosohandiary.domain.comment.service.CommentService;
import com.hanghae.sosohandiary.domain.comment.dto.CommentRequestDto;
import com.hanghae.sosohandiary.domain.comment.entity.Comment;
import com.hanghae.sosohandiary.security.MemberDetailsImpl;
import com.hanghae.sosohandiary.utils.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/detail/{detail-id}/comment")
    public List<CommentResponseDto> getComment(@PathVariable(name = "detail-id")Long id,
                                               @AuthenticationPrincipal MemberDetailsImpl memberDetails){
        return commentService.getComment(id, memberDetails.getMember());
    }

    @PatchMapping("/detail/{detail-id}/comment/{comment-id}")
    public CommentResponseDto updateComment(@PathVariable(name = "detail-id") Long detailId,
                                            @PathVariable(name = "comment-id") Long commentId,
                                            @RequestBody CommentRequestDto requestDto,
                                            @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return commentService.updateComment(detailId, commentId, requestDto, memberDetails.getMember());
    }

    @DeleteMapping("/detail/{detail-id}/comment/{comment-id}")
    public MessageDto deleteComment(@PathVariable(name = "detail-id") Long detailId,
                                    @PathVariable(name = "comment-id") Long commentId,
                                    @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return commentService.deleteComment(detailId, commentId, memberDetails.getMember());
    }


}
