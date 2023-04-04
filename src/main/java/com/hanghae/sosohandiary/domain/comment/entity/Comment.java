package com.hanghae.sosohandiary.domain.comment.entity;

import com.hanghae.sosohandiary.domain.comment.dto.CommentRequestDto;
import com.hanghae.sosohandiary.domain.diarydetail.entity.DiaryDetail;
import com.hanghae.sosohandiary.domain.member.entity.Member;
import com.hanghae.sosohandiary.utils.entity.Timestamp;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends Timestamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "diarydetail_id")
    private DiaryDetail diaryDetail;

    private String nickname;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private String comment;

    private boolean alarm;

    @Builder
    private Comment(DiaryDetail diaryDetail, Member member, CommentRequestDto commentRequestDto) {
        this.diaryDetail = diaryDetail;
        this.nickname = member.getNickname();
        this.comment = commentRequestDto.getComment();
        this.member = member;
    }

    public static Comment of(DiaryDetail diaryDetail, Member member, CommentRequestDto commentRequestDto) {
        return Comment.builder()
                .diaryDetail(diaryDetail)
                .member(member)
                .commentRequestDto(commentRequestDto)
                .build();
    }

    public void update(String commentRequestDto) {
        this.comment = commentRequestDto;
    }

    public void updateAlarm(boolean alarm) {
        this.alarm = alarm;
    }

}
