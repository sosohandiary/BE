package com.hanghae.sosohandiary.domain.comment.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hanghae.sosohandiary.domain.comment.dto.CommentRequestDto;
import com.hanghae.sosohandiary.domain.diarydetil.entity.DiaryDetail;
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


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private String contents;

    @Builder
    private Comment(DiaryDetail diaryDetail, Member member, String commentRequestDto) {
        this.diaryDetail = diaryDetail;
        this.contents = commentRequestDto;
        this.member = member;
    }

    public static Comment of(DiaryDetail diaryDetail, Member member, String commentRequestDto) {
        return Comment.builder()
                .diaryDetail(diaryDetail)
                .member(member)
                .commentRequestDto(commentRequestDto)
                .build();
    }

//    public void update(CommentsRequestDto requestDto){
//        //this.username=requestDto.getUsername();
//        this.contents=requestDto.getContents();
//        //this.userpassword=requestDto.getUserpassword();
//    }

}
