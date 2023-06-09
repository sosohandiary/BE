package com.hanghae.sosohandiary.domain.diary.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hanghae.sosohandiary.domain.diary.entity.Diary;
import com.hanghae.sosohandiary.domain.diary.entity.DiaryCondition;
import com.hanghae.sosohandiary.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class DiaryResponseDto {

    private Long id;
    private String img;
    private String title;
    private String nickname;
    private DiaryCondition diaryCondition;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;

    @Builder
    public DiaryResponseDto(Diary diary, Member member) {
        id = diary.getId();
        img = diary.getImg();
        title = diary.getTitle();
        nickname = member.getNickname();
        diaryCondition = diary.getDiaryCondition();
        createdAt = diary.getCreatedAt();
        modifiedAt = diary.getModifiedAt();
    }

    public static DiaryResponseDto of(Diary diary, Member member) {
        return DiaryResponseDto.builder()
                .diary(diary)
                .member(member)
                .build();
    }

}
