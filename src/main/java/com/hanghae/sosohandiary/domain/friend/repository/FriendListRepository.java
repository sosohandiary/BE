package com.hanghae.sosohandiary.domain.friend.repository;

import com.hanghae.sosohandiary.domain.friend.entity.FriendList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendListRepository extends JpaRepository<FriendList, Long> {
    
    Long countAllByMemberId(Long id);

    List<FriendList> findAllByMemberId(Long id);

    void deleteByMemberIdAndFriendId(Long memberId, Long friendId);

    void deleteByFriendIdAndMemberId(Long friendId, Long memberId);

}
