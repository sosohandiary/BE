package com.hanghae.sosohandiary.domain.diary.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hanghae.sosohandiary.domain.diary.entity.Diary;
import com.hanghae.sosohandiary.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class DiaryResponseDto {

    private Long id;
    private String img;
    private String content;
    private Member member;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;

    @Builder
    private DiaryResponseDto(Diary diary) {
        id = diary.getId();
        img = diary.getImg();
        content = diary.getContent();
//        this.member = member;
        createdAt = diary.getCreatedAt();
        modifiedAt = diary.getModifiedAt();
    }

    public static DiaryResponseDto from(Diary diary) {
        return DiaryResponseDto.builder()
                .diary(diary)
                .build();
    }

}
