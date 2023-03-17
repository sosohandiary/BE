package com.hanghae.sosohandiary.domain.myfriendsList.repository;

import com.hanghae.sosohandiary.domain.myfriendsList.entity.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRequestRepository extends JpaRepository<FriendRequest,Long> {
    boolean existsByFriend_IdAndMember_Id(Long id, Long id1);

    List<FriendRequest> findAllByFriendIdOrderByCreatedAtDesc(Long id);
}
