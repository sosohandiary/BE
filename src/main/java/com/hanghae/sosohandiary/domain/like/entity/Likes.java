package com.hanghae.sosohandiary.domain.like.entity;

import com.hanghae.sosohandiary.domain.diary.entity.Diary;
import com.hanghae.sosohandiary.domain.diarydetail.entity.DiaryDetail;
import com.hanghae.sosohandiary.domain.member.entity.Member;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_Id")
    private Diary diary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_Detail_Id")
    private DiaryDetail diaryDetail;

    @Builder
    private Likes(Member member,Diary diary, DiaryDetail diaryDetail) {
        this.member = member;
        this.diary = diary;
        this.diaryDetail = diaryDetail;
    }

    public static Likes of(Member member,Diary diary,DiaryDetail diaryDetail) {
        return Likes.builder()
                .diary(diary)
                .diaryDetail(diaryDetail)
                .member(member)
                .build();
    }
}
