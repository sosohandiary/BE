package com.hanghae.sosohandiary.domain.friend.service;

import com.hanghae.sosohandiary.domain.friend.dto.FriendResponseDto;
import com.hanghae.sosohandiary.domain.friend.entity.FriendList;
import com.hanghae.sosohandiary.domain.friend.entity.FriendRequest;
import com.hanghae.sosohandiary.domain.friend.repository.FriendListRepository;
import com.hanghae.sosohandiary.domain.friend.repository.FriendRequestRepository;
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
public class FriendsRequestService {
    private final FriendListRepository friendListRepository;
    private final FriendRequestRepository friendRequestRepository;
    private final MemberRepository memberRepository;


    @Transactional
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
        boolean isDuplicated = friendRequestRepository.existsByFriend_IdAndMember_Id(id, memberId.getId());
        if (isDuplicated) {
            return new MessageDto("이미 친구 요청이 존재합니다.", HttpStatus.BAD_REQUEST);
        }

        friendRequestRepository.save(FriendRequest.of(memberId, friendId));

        return new MessageDto("친구요청 성공", HttpStatus.CREATED);
    }

    public List<FriendResponseDto> getFriendRequest(Member member) {

        List<FriendRequest> friendRequestList = friendRequestRepository.findAllByFriendIdOrderByCreatedAtDesc(member.getId());
        List<FriendResponseDto> myFriendResponseDtoList = new ArrayList<>();

        for (FriendRequest friendRequest : friendRequestList) {
            myFriendResponseDtoList.add(FriendResponseDto.from(friendRequest.getMember().getNickname()));
        }
        return myFriendResponseDtoList;
    }

    @Transactional
    public MessageDto createFriendAccept(Long id, Member member) {
        FriendRequest friendRequest = friendRequestRepository.findById(id).orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_REQUEST)
        );

        if (!friendRequest.getFriend().getId().equals(member.getId())) {
            throw new ApiException(ErrorHandling.NOT_MATCH_AUTHORIZATION);
        }

        friendListRepository.save(FriendList.of(friendRequest.getMember(), friendRequest.getFriend()));
        friendListRepository.save(FriendList.of(friendRequest.getFriend(), friendRequest.getMember()));
        friendRequestRepository.deleteById(id);

        return new MessageDto("친구추가 성공", HttpStatus.ACCEPTED);
    }

}
