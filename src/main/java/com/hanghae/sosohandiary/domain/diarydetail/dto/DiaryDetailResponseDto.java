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
    private String customJson;
    private String diaryTitle;
    private String nickName;
    private String profileImageUrl;
    private String thumbnail;
    private int likeCount;
    private int commentCount;
    private boolean likeStatus;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;

    @Builder
    private DiaryDetailResponseDto(DiaryDetail diaryDetail, Diary diary, Member member, int likeCount, int commentCount, boolean likeStatus) {
        id = diaryDetail.getId();
        customJson = diaryDetail.getCustomJson();
        diaryTitle = diary.getTitle();
        nickName = member.getNickname();
        profileImageUrl = member.getProfileImageUrl();
        thumbnail = diaryDetail.getThumbnail();
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.likeStatus = likeStatus;
        createdAt = diaryDetail.getCreatedAt();
        modifiedAt = diaryDetail.getModifiedAt();
    }

    public static DiaryDetailResponseDto of(DiaryDetail diaryDetail, Diary diary, Member member) {
        return DiaryDetailResponseDto.builder()
                .diaryDetail(diaryDetail)
                .diary(diary)
                .member(member)
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

    public static DiaryDetailResponseDto of(DiaryDetail diaryDetail, Diary diary, Member member, int likeCount, int commentCount, boolean likeStatus) {
        return DiaryDetailResponseDto.builder()
                .diaryDetail(diaryDetail)
                .diary(diary)
                .member(member)
                .likeCount(likeCount)
                .commentCount(commentCount)
                .likeStatus(likeStatus)
                .build();
    }

}
