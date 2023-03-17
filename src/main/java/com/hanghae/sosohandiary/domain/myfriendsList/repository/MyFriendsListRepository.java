package com.hanghae.sosohandiary.domain.myfriendsList.repository;

import com.hanghae.sosohandiary.domain.myfriendsList.entity.MyFriendsList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyFriendsListRepository extends JpaRepository<MyFriendsList, Long> {
    List<MyFriendsList> findAllByMemberId(Long id);
}
