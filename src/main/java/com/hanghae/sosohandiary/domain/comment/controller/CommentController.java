package com.hanghae.sosohandiary.domain.comment.controller;

import com.hanghae.sosohandiary.domain.comment.dto.CommentResponseDto;
import com.hanghae.sosohandiary.domain.comment.service.CommentService;
import com.hanghae.sosohandiary.domain.comment.dto.CommentRequestDto;
import com.hanghae.sosohandiary.domain.comment.entity.Comment;
import com.hanghae.sosohandiary.security.MemberDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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


}
