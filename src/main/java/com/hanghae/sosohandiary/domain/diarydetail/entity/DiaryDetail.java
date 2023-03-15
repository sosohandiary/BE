package com.hanghae.sosohandiary.domain.diarydetail.entity;

import com.hanghae.sosohandiary.domain.diarydetail.dto.DiaryDetailRequestDto;
import com.hanghae.sosohandiary.domain.member.entity.Member;
import com.hanghae.sosohandiary.utils.entity.Timestamp;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryDetail extends Timestamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    // TODO: 2023-03-13 entity 회원 로직 완성 후 member 추가
    @Builder
    private DiaryDetail(DiaryDetailRequestDto diaryDetailRequestDto) {
        content = diaryDetailRequestDto.getContent();
//        this.member = member;
    }

    // TODO: 2023-03-13 entity 회원 로직 완성 후 member 추가
    public static DiaryDetail of(DiaryDetailRequestDto diaryDetailRequestDto) {
        return DiaryDetail.builder()
                .diaryDetailRequestDto(diaryDetailRequestDto)
//                .member(member)
                .build();
    }

}
