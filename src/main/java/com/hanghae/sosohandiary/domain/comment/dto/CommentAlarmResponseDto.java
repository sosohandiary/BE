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
public class CommentAlarmResponseDto {

    private Long diaryId;
    private Long diaryDetailId;
    private Long commentId;
    private String commentName;
    private boolean alarm;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;

    @Builder
    private CommentAlarmResponseDto(Long diaryId, DiaryDetail diaryDetail, Comment comment) {
        this.diaryId = diaryId;
        diaryDetailId = diaryDetail.getId();
        commentId = comment.getId();
        this.commentName = comment.getMember().getNickname();
        createdAt = comment.getCreatedAt();
        modifiedAt = comment.getModifiedAt();
        alarm = comment.isAlarm();
    }

    public static CommentAlarmResponseDto of(Long diaryId, DiaryDetail diaryDetail, Comment comment) {
        return CommentAlarmResponseDto.builder()
                .diaryId(diaryId)
                .diaryDetail(diaryDetail)
                .comment(comment)
                .build();
    }

}
