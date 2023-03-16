package com.hanghae.sosohandiary.domain.image.entity;

import com.hanghae.sosohandiary.domain.diary.entity.Diary;
import com.hanghae.sosohandiary.domain.diarydetil.entity.DiaryDetail;
import com.hanghae.sosohandiary.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryDetailImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String uploadPath;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "diarydetail_id", nullable = false)
    private DiaryDetail diaryDetail;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Builder
    private DiaryDetailImage(String uploadPath, DiaryDetail diaryDetail, Member member) {
        this.uploadPath = uploadPath;
        this.diaryDetail = diaryDetail;
        this.member = member;
    }

    public static DiaryDetailImage of(String uploadPath, DiaryDetail diaryDetail, Member member) {
        return DiaryDetailImage.builder()
                .uploadPath(uploadPath)
                .diaryDetail(diaryDetail)
                .member(member)
                .build();
    }
}
