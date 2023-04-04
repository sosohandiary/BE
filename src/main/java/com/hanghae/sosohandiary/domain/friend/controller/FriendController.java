package com.hanghae.sosohandiary.domain.friend.controller;

import com.hanghae.sosohandiary.domain.friend.dto.FriendResponseDto;
import com.hanghae.sosohandiary.domain.friend.service.FriendService;
import com.hanghae.sosohandiary.security.MemberDetailsImpl;
import com.hanghae.sosohandiary.utils.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friend")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendsListService;

    @PostMapping("/request/{member-id}")
    public MessageDto createFriendRequest(@PathVariable(value = "member-id") Long id,
                                          @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return friendsListService.createFriendRequest(id, memberDetails.getMember());
    }

    @GetMapping("/request")
    public List<FriendResponseDto> getFriendRequest(@AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return friendsListService.getFriendRequest(memberDetails.getMember());
    }

    @PutMapping("/request/accept/{friendrequest-id}")
    public MessageDto acceptFriend(@PathVariable(value = "friendrequest-id") Long id,
                                   @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return friendsListService.acceptFriend(id, memberDetails.getMember());
    }

    @GetMapping("/list")
    public List<FriendResponseDto> getFriendList(@AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return friendsListService.getFriendList(memberDetails.getMember());
    }

    @DeleteMapping("/list/{friend-id}")
    public MessageDto deleteFriendList(@PathVariable("friend-id") Long id,
                                       @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return friendsListService.deleteFriendList(id, memberDetails.getMember());
    }

    @PatchMapping("/request/read/{friendrequest-id}")
    public FriendResponseDto readRequest(@PathVariable(value = "friendrequest-id") Long id,
                                       @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return friendsListService.readRequest(id, memberDetails.getMember());
    }
}
