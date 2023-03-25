package com.hanghae.sosohandiary.domain.friend.entity;

import com.hanghae.sosohandiary.domain.friend.Enum.StatusFriend;
import com.hanghae.sosohandiary.domain.member.entity.Member;
import com.hanghae.sosohandiary.utils.entity.Timestamp;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FriendList extends Timestamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_id")
    private Member friend;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusFriend status;

    @Builder
    private FriendList(Member member, Member friend, StatusFriend status) {
        this.member = member;
        this.friend = friend;
        this.status = status;
    }

    public static FriendList of(Member member, Member friend, StatusFriend status) {
        return FriendList.builder()
                .member(member)
                .friend(friend)
                .status(status)
                .build();
    }

    public void updateFriendStatus(StatusFriend status){
        this.status=status;
    }

}
