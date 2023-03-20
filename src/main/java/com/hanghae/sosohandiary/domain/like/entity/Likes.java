package com.hanghae.sosohandiary.domain.like.entity;

import com.hanghae.sosohandiary.domain.diary.entity.Diary;
import com.hanghae.sosohandiary.domain.diarydetil.dto.DiaryDetailRequestDto;
import com.hanghae.sosohandiary.domain.diarydetil.entity.DiaryDetail;
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
    private DiaryDetail diaryDetail;

    @Builder
    private Likes(Member member,DiaryDetail diaryDetail) {
        this.member = member;
        this.diaryDetail = diaryDetail;
    }

    public static Likes of(Member member,DiaryDetail diaryDetail) {
        return Likes.builder()
                .diaryDetail(diaryDetail)
                .member(member)
                .build();
    }
}
