package com.hanghae.sosohandiary.domain.friend.repository;

import com.hanghae.sosohandiary.domain.friend.entity.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRequestRepository extends JpaRepository<FriendRequest,Long> {
    boolean existsByFriend_IdAndMember_Id(Long friendId, Long memberId);

    List<FriendRequest> findAllByFriendIdOrderByCreatedAtDesc(Long id);
}
