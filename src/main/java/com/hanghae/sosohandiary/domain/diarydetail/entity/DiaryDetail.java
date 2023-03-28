package com.hanghae.sosohandiary.domain.diarydetail.entity;

import com.hanghae.sosohandiary.domain.diary.entity.Diary;
import com.hanghae.sosohandiary.domain.diarydetail.dto.DiaryDetailRequestDto;
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
    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(columnDefinition = "TEXT")
    private String customJson;

    private String nickname;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;

    @Builder
    private DiaryDetail(DiaryDetailRequestDto diaryDetailRequestDto, Diary diary, Member member) {
        content = diaryDetailRequestDto.getContent();
        customJson = diaryDetailRequestDto.getCustomJson();
        nickname = member.getNickname();
        this.diary = diary;
    }

    public static DiaryDetail of(DiaryDetailRequestDto diaryDetailRequestDto, Diary diary, Member member) {
        return DiaryDetail.builder()
                .diaryDetailRequestDto(diaryDetailRequestDto)
                .member(member)
                .diary(diary)
                .build();
    }

    public static DiaryDetail of(DiaryDetailRequestDto diaryDetailRequestDto, Diary diary) {
        return DiaryDetail.builder()
                .diaryDetailRequestDto(diaryDetailRequestDto)
                .diary(diary)
                .build();
    }

    public static DiaryDetail of(DiaryDetailRequestDto diaryDetailRequestDto) {
        return DiaryDetail.builder()
                .diaryDetailRequestDto(diaryDetailRequestDto)
                .build();
    }

    public void update(DiaryDetailRequestDto diaryDetailRequestDto) {
        content = diaryDetailRequestDto.getContent();
        customJson = diaryDetailRequestDto.getCustomJson();
    }

}
