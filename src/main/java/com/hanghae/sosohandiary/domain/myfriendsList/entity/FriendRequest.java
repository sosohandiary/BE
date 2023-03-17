package com.hanghae.sosohandiary.domain.myfriendsList.entity;

import com.hanghae.sosohandiary.domain.member.entity.Member;
import com.hanghae.sosohandiary.utils.entity.Timestamp;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class FriendRequest extends Timestamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_id")
    private Member friend;

    @Builder
    public FriendRequest(Member member, Member friend) {
        this.member = member;
        this.friend = friend;
    }
    
    
}
