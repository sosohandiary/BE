package com.hanghae.sosohandiary.domain.diarydetil.entity;

import com.hanghae.sosohandiary.domain.diary.entity.Diary;
import com.hanghae.sosohandiary.domain.diarydetil.dto.DiaryDetailRequestDto;
import com.hanghae.sosohandiary.domain.member.entity.Member;
import com.hanghae.sosohandiary.utils.entity.Timestamp;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryDetail extends Timestamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    private String detailUploadPath;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    private DiaryDetail(DiaryDetailRequestDto diaryDetailRequestDto, String detailUploadPath, Diary diary, Member member) {
        content = diaryDetailRequestDto.getContent();
        this.detailUploadPath = detailUploadPath;
        this.diary = diary;
        this.member = member;
    }

    public static DiaryDetail of(DiaryDetailRequestDto diaryDetailRequestDto, String detailUploadPath, Diary diary, Member member) {
        return DiaryDetail.builder()
                .diaryDetailRequestDto(diaryDetailRequestDto)
                .detailUploadPath(detailUploadPath)
                .diary(diary)
                .member(member)
                .build();
    }

    public static DiaryDetail of(DiaryDetailRequestDto diaryDetailRequestDto, String detailUploadPath, Member member) {
        return DiaryDetail.builder()
                .diaryDetailRequestDto(diaryDetailRequestDto)
                .detailUploadPath(detailUploadPath)
                .member(member)
                .build();
    }

    public void update(DiaryDetailRequestDto diaryDetailRequestDto, String detailUploadPath) {
        content = diaryDetailRequestDto.getContent();
        this.detailUploadPath = detailUploadPath;
    }

}
