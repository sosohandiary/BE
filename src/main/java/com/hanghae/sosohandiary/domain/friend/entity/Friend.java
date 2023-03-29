package com.hanghae.sosohandiary.domain.friend.entity;

import com.hanghae.sosohandiary.domain.member.entity.Member;
import com.hanghae.sosohandiary.utils.entity.Timestamp;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Friend extends Timestamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, nullable = false)
    private String friendNickname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member friend;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusFriend status;

    @Builder
    private Friend(Member member, Member friend, StatusFriend status) {
        friendNickname = friend.getNickname();
        this.member = member;
        this.friend = friend;
        this.status = status;
    }

    public static Friend of(Member member, Member friend, StatusFriend status) {
        return Friend.builder()
                .member(member)
                .friend(friend)
                .status(status)
                .build();
    }

    public void updateFriendStatus(StatusFriend status) {
        this.status = status;
    }

}
