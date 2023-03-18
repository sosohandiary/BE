package com.hanghae.sosohandiary.domain.myfriendsList.repository;

import com.hanghae.sosohandiary.domain.myfriendsList.entity.MyFriendsList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MyFriendsListRepository extends JpaRepository<MyFriendsList, Long> {
    
    Long countAllByMemberId(Long id);

    List<MyFriendsList> findAllByMemberId(Long id);

    void deleteByMemberIdAndFriendId(Long memberId, Long friendId);

    void deleteByFriendIdAndMemberId(Long friendId, Long memberId);

//    Optional<MyFriendsList> findById(Long aLong);

}
