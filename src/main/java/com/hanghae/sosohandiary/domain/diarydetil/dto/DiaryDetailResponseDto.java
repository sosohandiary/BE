package com.hanghae.sosohandiary.domain.diarydetil.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hanghae.sosohandiary.domain.diary.entity.Diary;
import com.hanghae.sosohandiary.domain.diarydetil.entity.DiaryDetail;
import com.hanghae.sosohandiary.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class DiaryDetailResponseDto {

    private Long id;
    private List<String> imgList;
    private String content;
    private String name;
    private String diaryTitle;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;

    @Builder
    private DiaryDetailResponseDto(DiaryDetail diaryDetail, List<String> imgList, Diary diary, Member member) {
        id = diaryDetail.getId();
        this.imgList = imgList;
        content = diaryDetail.getContent();
        name = member.getName();
        diaryTitle = diary.getTitle();
        createdAt = diaryDetail.getCreatedAt();
        modifiedAt = diaryDetail.getModifiedAt();
    }

    public static DiaryDetailResponseDto from(DiaryDetail diaryDetail, List<String> imgList, Diary diary, Member member) {
        return DiaryDetailResponseDto.builder()
                .imgList(imgList)
                .diaryDetail(diaryDetail)
                .diary(diary)
                .member(member)
                .build();
    }

    public static DiaryDetailResponseDto from(DiaryDetail diaryDetail, Diary diary, Member member) {
        return DiaryDetailResponseDto.builder()
                .diaryDetail(diaryDetail)
                .diary(diary)
                .member(member)
                .build();
    }

    public static DiaryDetailResponseDto from(DiaryDetail diaryDetail, List<String> imgList, Member member) {
        return DiaryDetailResponseDto.builder()
                .diaryDetail(diaryDetail)
                .imgList(imgList)
                .member(member)
                .build();
    }

}
