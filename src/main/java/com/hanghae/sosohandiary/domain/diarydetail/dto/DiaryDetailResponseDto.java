package com.hanghae.sosohandiary.domain.diarydetail.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hanghae.sosohandiary.domain.diarydetail.entity.DiaryDetail;
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
    private Member member;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;

    @Builder
    private DiaryDetailResponseDto(DiaryDetail diaryDetail, List<String> imgLIst, Member member) {
        id = diaryDetail.getId();
        this.imgList = imgLIst;
        content = diaryDetail.getContent();
        this.member = member;
        createdAt = diaryDetail.getCreatedAt();
        modifiedAt = diaryDetail.getModifiedAt();
    }

    public static DiaryDetailResponseDto from(DiaryDetail diaryDetail, List<String> imgLIst) {
        return DiaryDetailResponseDto.builder()
                .imgLIst(imgLIst)
                .diaryDetail(diaryDetail)
                .build();
    }

    public static DiaryDetailResponseDto from(DiaryDetail diaryDetail, Member member) {
        return DiaryDetailResponseDto.builder()
                .diaryDetail(diaryDetail)
                .member(member)
                .build();
    }

}
