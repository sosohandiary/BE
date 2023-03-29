package com.hanghae.sosohandiary.domain.diary.entity;

import com.hanghae.sosohandiary.domain.diary.dto.DiaryRequestDto;
import com.hanghae.sosohandiary.domain.member.entity.Member;
import com.hanghae.sosohandiary.utils.entity.Timestamp;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Diary extends Timestamp {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String title;

    private String img;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(length = 10, nullable = false)
    @Enumerated(value = STRING)
    private DiaryCondition diaryCondition;

    @Builder
    private Diary(DiaryRequestDto diaryRequestDto, String uploadPath, Member member) {
        title = diaryRequestDto.getTitle();
        this.img = uploadPath;
        this.member = member;
        this.diaryCondition = diaryRequestDto.getDiaryCondition();
    }

    public static Diary of(DiaryRequestDto diaryRequestDto, String uploadPath, Member member) {
        return Diary.builder()
                .diaryRequestDto(diaryRequestDto)
                .uploadPath(uploadPath)
                .member(member)
                .build();
    }

    public static Diary of(DiaryRequestDto diaryRequestDto, Member member) {
        return Diary.builder()
                .diaryRequestDto(diaryRequestDto)
                .member(member)
                .build();
    }

    public void update(DiaryRequestDto diaryRequestDto, String uploadPath) {
        title = diaryRequestDto.getTitle();
        this.img = uploadPath;
    }


}
