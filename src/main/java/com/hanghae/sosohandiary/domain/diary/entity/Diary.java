package com.hanghae.sosohandiary.domain.diary.entity;

import com.hanghae.sosohandiary.domain.diary.dto.DiaryRequestDto;
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
public class Diary extends Timestamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String img;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    // TODO: 2023-03-13 entity 회원 로직 완성 후 member 추가
    @Builder
    private Diary(DiaryRequestDto diaryRequestDto) {
        img = diaryRequestDto.getImg();
        content = diaryRequestDto.getContent();
//        this.member = member;
    }

    // TODO: 2023-03-13 entity 회원 로직 완성 후 member 추가
    public static Diary of(DiaryRequestDto diaryRequestDto) {
        return Diary.builder()
                .diaryRequestDto(diaryRequestDto)
//                .member(member)
                .build();
    }

}
