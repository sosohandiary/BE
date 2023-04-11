package com.hanghae.sosohandiary.domain.comment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hanghae.sosohandiary.domain.comment.entity.Comment;
import com.hanghae.sosohandiary.domain.diarydetail.entity.DiaryDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class CommentResponseDto {
    private Long diaryDetailId;
    private Long commentId;
    private String commentName;
    private String commentProfileImageUrl;
    private String comment;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;

    @Builder
    private CommentResponseDto(DiaryDetail diaryDetail, Comment comment) {
        diaryDetailId = diaryDetail.getId();
        commentId = comment.getId();
        this.comment = comment.getComment();
        commentName = comment.getMember().getNickname();
        commentProfileImageUrl = comment.getMember().getProfileImageUrl();
        createdAt = comment.getCreatedAt();
        modifiedAt = comment.getModifiedAt();
    }

    public static CommentResponseDto of(DiaryDetail diaryDetail, Comment comment) {
        return CommentResponseDto.builder()
                .diaryDetail(diaryDetail)
                .comment(comment)
                .build();
    }

}
