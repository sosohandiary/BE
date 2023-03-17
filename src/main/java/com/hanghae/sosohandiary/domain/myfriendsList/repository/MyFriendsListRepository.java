package com.hanghae.sosohandiary.domain.myfriendsList.repository;

import com.hanghae.sosohandiary.domain.myfriendsList.entity.MyFriendsList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyFriendsListRepository extends JpaRepository<MyFriendsList, Long> {
}
