package com.hanghae.sosohandiary.domain.diary.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hanghae.sosohandiary.domain.diary.entity.Diary;
import com.hanghae.sosohandiary.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class DiaryResponseDto {

    private Long id;
    private List<String> imgList;
    private String title;
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;

    @Builder
    private DiaryResponseDto(Diary diary, List<String> imgList, Member member) {
        id = diary.getId();
        this.imgList = imgList;
        title = diary.getTitle();
        name = member.getName();
        createdAt = diary.getCreatedAt();
        modifiedAt = diary.getModifiedAt();
    }

    public static DiaryResponseDto from(Diary diary, List<String> imgList, Member member) {
        return DiaryResponseDto.builder()
                .imgList(imgList)
                .diary(diary)
                .member(member)
                .build();
    }

    public static DiaryResponseDto from(Diary diary, Member member) {
        return DiaryResponseDto.builder()
                .diary(diary)
                .member(member)
                .build();
    }

}
