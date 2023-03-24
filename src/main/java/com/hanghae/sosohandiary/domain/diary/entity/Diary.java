package com.hanghae.sosohandiary.domain.diary.entity;

import com.hanghae.sosohandiary.domain.diary.dto.DiaryRequestDto;
import com.hanghae.sosohandiary.domain.invite.entity.Invite;
import com.hanghae.sosohandiary.domain.member.entity.Member;
import com.hanghae.sosohandiary.utils.entity.Timestamp;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

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

    private String img;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(length = 10, nullable = false)
    @Enumerated(value = EnumType.STRING)
    private DiaryCondition diaryCondition;

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL)
    private List<Invite> inviteDiaryList = new ArrayList<>();

    @Builder
    private Diary(DiaryRequestDto diaryRequestDto, String uploadPath, Member member, List<Invite> inviteDiaryList) {
        title = diaryRequestDto.getTitle();
        this.img = uploadPath;
        this.member = member;
        this.diaryCondition = diaryRequestDto.getDiaryCondition();
        this.inviteDiaryList = inviteDiaryList;
    }

    public static Diary of(DiaryRequestDto diaryRequestDto, String uploadPath, Member member) {
        return Diary.builder()
                .diaryRequestDto(diaryRequestDto)
                .uploadPath(uploadPath)
                .member(member)
                .build();
    }
//
//    public static Diary of(DiaryRequestDto diaryRequestDto, String uploadPath, Member member) {
//        return Diary.builder()
//                .diaryRequestDto(diaryRequestDto)
//                .uploadPath(uploadPath)
//                .member(member)
//                .build();
//    }

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

    public void setInviteDiaryList(Invite invite) {
        inviteDiaryList.add(invite);
    }

}
