package com.hanghae.sosohandiary.domain.diarydetail.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hanghae.sosohandiary.domain.diary.entity.Diary;
import com.hanghae.sosohandiary.domain.diarydetail.entity.DiaryDetail;
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
    private String nickname;
    private int likes;
    private int comment;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;

    @Builder
    public DiaryDetailResponseDto(DiaryDetail diaryDetail, Diary diary, Member member, int likes, int comment) {
        id = diaryDetail.getId();
        content = diaryDetail.getContent();
        customJson = diaryDetail.getCustomJson();
        diaryTitle = diary.getTitle();
        nickname = member.getNickname();
        this.likes = likes;
        this.comment = comment;
        createdAt = diaryDetail.getCreatedAt();
        modifiedAt = diaryDetail.getModifiedAt();
    }

    public static DiaryDetailResponseDto from(DiaryDetail diaryDetail, Diary diary, Member member) {
        return DiaryDetailResponseDto.builder()
                .diaryDetail(diaryDetail)
                .member(member)
                .diary(diary)
                .build();
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

    public static DiaryDetailResponseDto from(DiaryDetail diaryDetail, Diary diary, int likes, int comment) {
        return DiaryDetailResponseDto.builder()
                .diaryDetail(diaryDetail)
                .diary(diary)
                .likes(likes)
                .comment(comment)
                .build();
    }

}
