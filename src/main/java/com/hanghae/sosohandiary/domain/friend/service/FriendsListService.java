package com.hanghae.sosohandiary.domain.friend.service;

import com.hanghae.sosohandiary.domain.friend.dto.FriendResponseDto;
import com.hanghae.sosohandiary.domain.friend.entity.FriendList;
import com.hanghae.sosohandiary.domain.friend.repository.FriendListRepository;
import com.hanghae.sosohandiary.domain.member.entity.Member;
import com.hanghae.sosohandiary.exception.ApiException;
import com.hanghae.sosohandiary.exception.ErrorHandling;
import com.hanghae.sosohandiary.utils.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FriendsListService {
    private final FriendListRepository friendListRepository;

    public List<FriendResponseDto> getFriendList(Member member) {
        List<FriendList> FriendLists = friendListRepository.findAllByMemberId(member.getId());
        List<FriendResponseDto> friendListResponseDtoList = new ArrayList<>();

        for (FriendList friendList : FriendLists) {
            friendListResponseDtoList.add(FriendResponseDto.from(friendList.getFriend().getNickname()));
        }
        return friendListResponseDtoList;
    }

    @Transactional
    public MessageDto deleteFriendList(Long id, Member member) {
        FriendList friendList = friendListRepository.findById(id).orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_FOUND_USER)
        );

        if (!friendList.getMember().getId().equals(member.getId())) {
            throw new ApiException(ErrorHandling.NOT_MATCH_AUTHORIZATION);
        }

        friendListRepository.deleteByMemberIdAndFriendId(friendList.getMember().getId(), friendList.getFriend().getId());
        friendListRepository.deleteByFriendIdAndMemberId(friendList.getMember().getId(), friendList.getFriend().getId());
        return MessageDto.of("친구 삭제 완료", HttpStatus.OK);
    }
}
