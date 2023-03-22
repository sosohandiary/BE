package com.hanghae.sosohandiary.domain.invite.entity;

import com.hanghae.sosohandiary.domain.diary.entity.Diary;
import com.hanghae.sosohandiary.domain.friend.entity.FriendList;
import com.hanghae.sosohandiary.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Invite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member fromMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member toMember;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Diary diary;

    @Builder
    private Invite(Member fromMember, Member toMember, Diary diary) {
        this.fromMember = fromMember;
        this.toMember = toMember;
        this.diary = diary;
    }

    public static Invite of(Member fromMember, Member toMember, Diary diary) {
        return Invite.builder()
                .diary(diary)
                .fromMember(fromMember)
                .toMember(toMember)
                .build();
    }


}
