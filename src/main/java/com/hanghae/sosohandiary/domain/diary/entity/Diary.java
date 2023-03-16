package com.hanghae.sosohandiary.domain.diary.entity;

import com.hanghae.sosohandiary.domain.diary.dto.DiaryRequestDto;
import com.hanghae.sosohandiary.domain.member.entity.Member;
import com.hanghae.sosohandiary.utils.entity.Timestamp;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Diary extends Timestamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String title;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    private Diary(DiaryRequestDto diaryRequestDto, Member member) {
        title = diaryRequestDto.getTitle();
        this.member = member;
    }

    public static Diary of(DiaryRequestDto diaryRequestDto, Member member) {
        return Diary.builder()
                .diaryRequestDto(diaryRequestDto)
                .member(member)
                .build();
    }

    public void update(DiaryRequestDto diaryRequestDto) {
        title = diaryRequestDto.getTitle();
    }
}
