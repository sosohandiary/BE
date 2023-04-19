package com.hanghae.sosohandiary.domain.diarydetail.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hanghae.sosohandiary.domain.diary.entity.Diary;
import com.hanghae.sosohandiary.domain.diarydetail.entity.DiaryDetail;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class DiaryDetailResponseDto {

    private Long id;
    private Long authorId;
    private String customJson;
    private String diaryTitle;
    private String nickname;
    private String profileImageUrl;
    private String thumbnail;
    private int likeCount;
    private int commentCount;
    private boolean likeStatus;
    private List<Long> toMemberId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;

    @Builder
    private DiaryDetailResponseDto(DiaryDetail diaryDetail, Diary diary, Long authorId, List<Long> toMemberId, int likeCount, int commentCount, boolean likeStatus) {
        id = diaryDetail.getId();
        this.authorId =  authorId;
        customJson = diaryDetail.getCustomJson();
        diaryTitle = diary.getTitle();
        nickname = diaryDetail.getMember().getNickname();
        profileImageUrl = diaryDetail.getMember().getProfileImageUrl();
        thumbnail = diaryDetail.getThumbnail();
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.likeStatus = likeStatus;
        this.toMemberId = toMemberId;
        createdAt = diaryDetail.getCreatedAt();
        modifiedAt = diaryDetail.getModifiedAt();

    }

    public static DiaryDetailResponseDto of(DiaryDetail diaryDetail, Diary diary, Long authorId, List<Long> toMemberId,
                                            int likeCount, int commentCount, boolean likeStatus) {
        return DiaryDetailResponseDto.builder()
                .diaryDetail(diaryDetail)
                .diary(diary)
                .authorId(authorId)
                .toMemberId(toMemberId)
                .likeCount(likeCount)
                .commentCount(commentCount)
                .likeStatus(likeStatus)
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

    public static DiaryDetailResponseDto of(DiaryDetail diaryDetail, Diary diary) {
        return DiaryDetailResponseDto.builder()
                .diaryDetail(diaryDetail)
                .diary(diary)
                .build();
    }


}
