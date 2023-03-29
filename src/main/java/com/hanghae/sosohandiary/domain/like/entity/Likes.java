package com.hanghae.sosohandiary.domain.like.entity;

import com.hanghae.sosohandiary.domain.diarydetail.entity.DiaryDetail;
import com.hanghae.sosohandiary.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Entity
@NoArgsConstructor
public class Likes {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "diary_Detail_Id")
    private DiaryDetail diaryDetail;

    @Builder
    private Likes(Member member, DiaryDetail diaryDetail) {
        this.member = member;
        this.diaryDetail = diaryDetail;
    }

    public static Likes of(Member member, DiaryDetail diaryDetail) {
        return Likes.builder()
                .diaryDetail(diaryDetail)
                .member(member)
                .build();
    }
}
