package com.hanghae.sosohandiary.domain.comment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hanghae.sosohandiary.domain.comment.entity.Comment;
import com.hanghae.sosohandiary.domain.diarydetail.entity.DiaryDetail;
import com.hanghae.sosohandiary.domain.member.entity.Member;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class CommentResponseDto {
    private Long diaryDetailId;
    private Long commentId;
    private String commentName;
    private String comment;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;

    @Builder
    private CommentResponseDto(DiaryDetail diaryDetail,Member member, Comment comment) {
        diaryDetailId = diaryDetail.getId();
        commentId = comment.getId();
        this.comment = comment.getComment();
        this.commentName = member.getName();
        createdAt = diaryDetail.getCreatedAt();
        modifiedAt = diaryDetail.getModifiedAt();
    }


    public static CommentResponseDto of(DiaryDetail diaryDetail, Member member, Comment comment) {
        return CommentResponseDto.builder()
                .diaryDetail(diaryDetail)
                .member(member)
                .comment(comment)
                .build();
    }

}
