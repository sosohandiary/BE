package com.hanghae.sosohandiary.domain.myfriendsList.controller;

import com.hanghae.sosohandiary.domain.myfriendsList.dto.FriendListResponseDto;
import com.hanghae.sosohandiary.domain.myfriendsList.dto.MyFriendResponseDto;
import com.hanghae.sosohandiary.domain.myfriendsList.service.MyFriendsListService;
import com.hanghae.sosohandiary.security.MemberDetailsImpl;
import com.hanghae.sosohandiary.utils.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friend")
@RequiredArgsConstructor
public class MyFriendsListController {
    private final MyFriendsListService myFriendsListService;

    @PostMapping("/request/add/{id}")
    public MessageDto createFriendRequest(@PathVariable Long id, @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return myFriendsListService.createFriendRequest(id, memberDetails.getMember());
    }

    @GetMapping("/request/add")
    public List<MyFriendResponseDto> getFriendRequest(@AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return myFriendsListService.getFriendRequest(memberDetails.getMember());
    }

    @PostMapping("/add/{id}")
    public MessageDto createFriendAccept(@PathVariable Long id, @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return myFriendsListService.createFriendAccept(id, memberDetails.getMember());
    }

    @GetMapping("/friendlist")
    public List<FriendListResponseDto> getFriendList(@AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return myFriendsListService.getFriendList(memberDetails.getMember());
    }

    @DeleteMapping("/firendlist/{friendlist-id}")
    public MessageDto deleteFriendList(@PathVariable(name = "friendlist-id") Long id,
                                       @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return myFriendsListService.deleteFriendList(id, memberDetails.getMember());
    }
}
