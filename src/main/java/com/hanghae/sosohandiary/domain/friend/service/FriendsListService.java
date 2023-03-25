package com.hanghae.sosohandiary.domain.friend.service;

import com.hanghae.sosohandiary.domain.friend.Enum.StatusFriend;
import com.hanghae.sosohandiary.domain.friend.dto.FriendResponseDto;
import com.hanghae.sosohandiary.domain.friend.entity.FriendList;
import com.hanghae.sosohandiary.domain.friend.entity.FriendRequest;
import com.hanghae.sosohandiary.domain.friend.repository.FriendListRepository;
import com.hanghae.sosohandiary.domain.member.entity.Member;
import com.hanghae.sosohandiary.domain.member.repository.MemberRepository;
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
    private final MemberRepository memberRepository;

    public MessageDto createFriendRequest(Long id, Member member) {
        if (member.getId().equals(id)) {
            return new MessageDto("자기 자신을 추가할 수 없습니다.", HttpStatus.BAD_REQUEST);
        }

        Member memberId = memberRepository.findById(member.getId()).orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_FOUND_USER)
        );
        Member friendId = memberRepository.findById(id).orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_FOUND_USER)
        );
        // 친구 요청 중복 체크
        boolean isDuplicated = friendListRepository.existsByFriend_IdAndMember_Id(id, memberId.getId());
        if (isDuplicated) {
            return new MessageDto("이미 상대방에게 친구 요청 하였습니다.", HttpStatus.BAD_REQUEST);
        }


        friendListRepository.save(FriendList.of(memberId, friendId, StatusFriend.PENDING));

        return new MessageDto("친구요청 성공", HttpStatus.CREATED);

    }

    public List<FriendResponseDto> getFriendRequest(Member member) {
        List<FriendList> friendRequestList = friendListRepository.findByFriendIdAndStatusOrderByCreatedAtDesc(member.getId(), StatusFriend.PENDING);
        List<FriendResponseDto> myFriendResponseDtoList = new ArrayList<>();

        for (FriendList friendRequest : friendRequestList) {
            myFriendResponseDtoList.add(FriendResponseDto.from(friendRequest));
        }
        return myFriendResponseDtoList;
    }

    @Transactional
    public MessageDto acceptFriend(Long id, Member member) {

        FriendList friendAccept = friendListRepository.findById(id).orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_REQUEST)
        );

        Member memberId = memberRepository.findById(member.getId()).orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_FOUND_USER)
        );
        Member friendId = memberRepository.findById(friendAccept.getMember().getId()).orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_FOUND_USER)
        );

        friendAccept.updateFriendStatus(StatusFriend.ACCEPTED);

        friendListRepository.save(FriendList.of(memberId, friendId, StatusFriend.ACCEPTED));
        return new MessageDto("친구추가 성공", HttpStatus.ACCEPTED);
    }

    public List<FriendResponseDto> getFriendList(Member member) {
        List<FriendList> friendAccept = friendListRepository.findByFriendIdAndStatusOrderByCreatedAtDesc(member.getId(), StatusFriend.ACCEPTED);
        List<FriendResponseDto> friendListResponseDtoList = new ArrayList<>();

        for (FriendList friendList : friendAccept) {
            friendListResponseDtoList.add(FriendResponseDto.from(friendList));
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
