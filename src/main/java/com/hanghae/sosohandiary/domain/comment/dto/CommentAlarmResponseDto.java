package com.hanghae.sosohandiary.domain.comment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hanghae.sosohandiary.domain.comment.entity.Comment;
import com.hanghae.sosohandiary.domain.diarydetail.entity.DiaryDetail;
import com.hanghae.sosohandiary.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class CommentAlarmResponseDto {
    private Long diaryDetailId;
    private Long commentId;
    private String commentName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;

    @Builder
    private CommentAlarmResponseDto(DiaryDetail diaryDetail, Comment comment) {
        diaryDetailId = diaryDetail.getId();
        commentId = comment.getId();
        this.commentName = comment.getNickname();
        createdAt = comment.getCreatedAt();
        modifiedAt = comment.getModifiedAt();
    }


    public static CommentAlarmResponseDto of(DiaryDetail diaryDetail, Comment comment) {
        return CommentAlarmResponseDto.builder()
                .diaryDetail(diaryDetail)
                .comment(comment)
                .build();
    }

}
