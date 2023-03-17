package com.hanghae.sosohandiary.domain.myfriendsList.controller;

import com.hanghae.sosohandiary.domain.myfriendsList.service.MyFriendsListService;
import com.hanghae.sosohandiary.security.MemberDetailsImpl;
import com.hanghae.sosohandiary.utils.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/friend")
@RequiredArgsConstructor
public class MyFriendsListController {
    private final MyFriendsListService myFriendsListService;

    @PostMapping("/request/add/{id}")
    public MessageDto createFriendRequest(@PathVariable Long id, @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return myFriendsListService.createFriendRequest(id, memberDetails.getMember());
    }

    @PostMapping("/add/{id}")
    public MessageDto createFriendAccept(@PathVariable Long id, @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return myFriendsListService.createFriendAccept(id, memberDetails.getMember());
    }
}
