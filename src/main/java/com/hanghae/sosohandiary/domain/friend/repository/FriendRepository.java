package com.hanghae.sosohandiary.domain.friend.repository;

import com.hanghae.sosohandiary.domain.friend.entity.Friend;
import com.hanghae.sosohandiary.domain.friend.entity.StatusFriend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    List<Friend> findAllByMemberId(Long id);

    Optional<Friend> findByIdAndFriendId(Long id, Long friendId);

    void deleteByMemberIdAndFriendId(Long memberId, Long friendId);

    void deleteByFriendIdAndMemberId(Long friendId, Long memberId);

    boolean existsByFriend_IdAndMember_Id(Long friendId, Long memberId);

    List<Friend> findByMemberIdAndStatusOrderByFriendNicknameAsc(Long id, StatusFriend Status);

    List<Friend> findByFriendIdAndStatusOrderByCreatedAtDesc(Long id, StatusFriend Status);

    void deleteAllByFriendId(Long id);

    Long countAllByMemberIdAndStatus(Long id, StatusFriend accepted);

    boolean existsByFriendIdAndMemberIdAndStatus(Long friendId, Long memberId, StatusFriend Status);
}
