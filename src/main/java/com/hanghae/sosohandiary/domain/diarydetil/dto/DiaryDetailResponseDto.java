package com.hanghae.sosohandiary.domain.diarydetil.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hanghae.sosohandiary.domain.diary.entity.Diary;
import com.hanghae.sosohandiary.domain.diarydetil.entity.DiaryDetail;
import com.hanghae.sosohandiary.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class DiaryDetailResponseDto {

    private Long id;
    private String content;
    private String customJson;
    private String diaryTitle;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;

    @Builder
    public DiaryDetailResponseDto(DiaryDetail diaryDetail, Diary diary) {
        id = diaryDetail.getId();
        content = diaryDetail.getContent();
        customJson = diaryDetail.getCustomJson();
        diaryTitle = diary.getTitle();
        createdAt = diaryDetail.getCreatedAt();
        modifiedAt = diaryDetail.getModifiedAt();
    }

    public static DiaryDetailResponseDto from(DiaryDetail diaryDetail, Diary diary) {
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

}
