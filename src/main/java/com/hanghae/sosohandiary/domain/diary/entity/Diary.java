package com.hanghae.sosohandiary.domain.diary.entity;

import com.hanghae.sosohandiary.domain.diary.dto.DiaryRequestDto;
import com.hanghae.sosohandiary.domain.image.entity.DiaryImage;
import com.hanghae.sosohandiary.domain.member.entity.Member;
import com.hanghae.sosohandiary.utils.entity.Timestamp;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.*;

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

    // TODO: 2023-03-13 entity 회원 로직 완성 후 member 추가
    @Builder
    private Diary(DiaryRequestDto diaryRequestDto, Member member) {
        title = diaryRequestDto.getTitle();
        this.member = member;
    }

    // TODO: 2023-03-13 entity 회원 로직 완성 후 member 추가
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
