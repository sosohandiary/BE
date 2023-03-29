package com.hanghae.sosohandiary.domain.diarydetail.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hanghae.sosohandiary.domain.diary.entity.Diary;
import com.hanghae.sosohandiary.domain.diarydetail.entity.DiaryDetail;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class DiaryDetailResponseDto {

    private Long id;
    private String customJson;
    private String diaryTitle;
    private String nickname;
    private int likeCount;
    private int commentCount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;

    @Builder
    private DiaryDetailResponseDto(DiaryDetail diaryDetail, Diary diary, int likeCount, int commentCount) {
        id = diaryDetail.getId();
        customJson = diaryDetail.getCustomJson();
        diaryTitle = diary.getTitle();
        nickname = diaryDetail.getNickname();
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        createdAt = diaryDetail.getCreatedAt();
        modifiedAt = diaryDetail.getModifiedAt();
    }

    public static DiaryDetailResponseDto of(DiaryDetail diaryDetail, Diary diary) {
        return DiaryDetailResponseDto.builder()
                .diaryDetail(diaryDetail)
                .diary(diary)
                .build();
    }

    public static DiaryDetailResponseDto from(DiaryDetail diaryDetail) {
        return DiaryDetailResponseDto.builder()
                .diaryDetail(diaryDetail)
                .build();
    }

    public static DiaryDetailResponseDto of(DiaryDetail diaryDetail, Diary diary, int likeCount, int commentCount) {
        return DiaryDetailResponseDto.builder()
                .diaryDetail(diaryDetail)
                .diary(diary)
                .likeCount(likeCount)
                .commentCount(commentCount)
                .build();
    }

}
