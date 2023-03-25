package com.hanghae.sosohandiary.domain.friend.repository;

import com.hanghae.sosohandiary.domain.friend.entity.Enum.StatusFriend;
import com.hanghae.sosohandiary.domain.friend.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    Long countAllByMemberId(Long id);

    List<Friend> findAllByMemberId(Long id);

    void deleteByMemberIdAndFriendId(Long memberId, Long friendId);

    void deleteByFriendIdAndMemberId(Long friendId, Long memberId);

    boolean existsByFriend_IdAndMember_Id(Long friendId, Long memberId);

    List<Friend> findByMemberIdAndStatusOrderByFriendNicknameAsc(Long id, StatusFriend Status);

    List<Friend> findByFriendIdAndStatusOrderByCreatedAtDesc(Long id, StatusFriend Status);
}
