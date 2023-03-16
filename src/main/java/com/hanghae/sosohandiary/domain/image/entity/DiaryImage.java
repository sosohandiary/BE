package com.hanghae.sosohandiary.domain.image.entity;

import com.hanghae.sosohandiary.domain.diary.entity.Diary;
import com.hanghae.sosohandiary.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String uploadPath;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "diary_id", nullable = false)
    private Diary diary;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Builder
    private DiaryImage(String uploadPath, Diary diary, Member member) {
        this.uploadPath = uploadPath;
        this.diary = diary;
        this.member = member;
    }

    public static DiaryImage of(String uploadPath, Diary diary, Member member) {
        return DiaryImage.builder()
                .uploadPath(uploadPath)
                .diary(diary)
                .member(member)
                .build();
    }

}
