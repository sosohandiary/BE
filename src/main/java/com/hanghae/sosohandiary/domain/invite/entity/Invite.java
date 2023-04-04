package com.hanghae.sosohandiary.domain.invite.entity;

import com.hanghae.sosohandiary.domain.diary.entity.Diary;
import com.hanghae.sosohandiary.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;
import static org.hibernate.annotations.OnDeleteAction.CASCADE;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Invite {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "from_member_id")
    @OnDelete(action = CASCADE)
    private Member fromMember;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "to_member_id")
    @OnDelete(action = CASCADE)
    private Member toMember;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "diary_id")
    @OnDelete(action = CASCADE)
    private Diary diary;

    private boolean alarm;

    @Builder
    private Invite(Member fromMember, Member toMember, Diary diary, boolean alarm) {
        this.fromMember = fromMember;
        this.toMember = toMember;
        this.diary = diary;
        this.alarm = alarm;
    }

    public static Invite of(Member fromMember, Member toMember, Diary diary, boolean alarm) {
        return Invite.builder()
                .diary(diary)
                .fromMember(fromMember)
                .toMember(toMember)
                .alarm(alarm)
                .build();
    }

    public static Invite of(Member fromMember, Member toMember, Diary diary) {
        return Invite.builder()
                .diary(diary)
                .fromMember(fromMember)
                .toMember(toMember)
                .build();
    }

    public void updateAlarm(boolean alarm) {
        this.alarm = alarm;
    }

}
