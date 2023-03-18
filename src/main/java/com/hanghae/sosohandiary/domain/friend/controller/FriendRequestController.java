package com.hanghae.sosohandiary.domain.friend.controller;

import com.hanghae.sosohandiary.domain.friend.dto.FriendResponseDto;
import com.hanghae.sosohandiary.domain.friend.service.FriendsRequestService;
import com.hanghae.sosohandiary.security.MemberDetailsImpl;
import com.hanghae.sosohandiary.utils.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friend")
@RequiredArgsConstructor
public class FriendRequestController {
    private final FriendsRequestService friendsRequestService;

    @PostMapping("/request/{id}")
    public MessageDto createFriendRequest(@PathVariable Long id,
                                          @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return friendsRequestService.createFriendRequest(id, memberDetails.getMember());
    }

    @GetMapping("/request")
    public List<FriendResponseDto> getFriendRequest(@AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return friendsRequestService.getFriendRequest(memberDetails.getMember());
    }

    @PostMapping("/request/accept/{id}")
    public MessageDto createFriendAccept(@PathVariable Long id,
                                         @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return friendsRequestService.createFriendAccept(id, memberDetails.getMember());
    }

}
