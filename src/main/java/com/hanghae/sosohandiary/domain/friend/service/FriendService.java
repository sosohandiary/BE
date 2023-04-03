package com.hanghae.sosohandiary.domain.friend.service;

import com.hanghae.sosohandiary.domain.friend.dto.FriendResponseDto;
import com.hanghae.sosohandiary.domain.friend.entity.Friend;
import com.hanghae.sosohandiary.domain.friend.repository.FriendRepository;
import com.hanghae.sosohandiary.domain.member.entity.Member;
import com.hanghae.sosohandiary.domain.member.repository.MemberRepository;
import com.hanghae.sosohandiary.exception.ApiException;
import com.hanghae.sosohandiary.utils.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.hanghae.sosohandiary.domain.friend.entity.StatusFriend.ACCEPTED;
import static com.hanghae.sosohandiary.domain.friend.entity.StatusFriend.PENDING;
import static com.hanghae.sosohandiary.exception.ErrorHandling.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FriendService {
    private final FriendRepository friendRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public MessageDto createFriendRequest(Long id, Member member) {

        if (member.getId().equals(id)) {
            throw new ApiException(INVALID_ACCESS);
        }

        Member memberId = memberRepository.findById(member.getId()).orElseThrow(
                () -> new ApiException(NOT_FOUND_USER)
        );
        Member friendId = memberRepository.findById(id).orElseThrow(
                () -> new ApiException(NOT_FOUND_USER)
        );

        boolean isDuplicated = friendRepository.existsByFriend_IdAndMember_Id(id, memberId.getId());
        if (isDuplicated) {
            throw new ApiException(DUPLICATED_REQUEST);
        }

        friendRepository.save(Friend.of(memberId, friendId, PENDING));

        return MessageDto.of("친구요청 성공", HttpStatus.CREATED);

    }

    public List<FriendResponseDto> getFriendRequest(Member member) {

        List<Friend> friendList = friendRepository.findByFriendIdAndStatusOrderByCreatedAtDesc(member.getId(), PENDING);
        List<FriendResponseDto> friendResponseDtoList = new ArrayList<>();

        for (Friend friend : friendList) {
            friendResponseDtoList.add(FriendResponseDto
                    .of(friend.getId(), friend.getMember().getNickname(), friend.getFriend().getNickname(), friend.getFriend().getProfileImageUrl()));
        }
        return friendResponseDtoList;
    }

    @Transactional
    public MessageDto acceptFriend(Long id, Member member) {

        Friend friendAccept = friendRepository.findByIdAndFriendId(id, member.getId()).orElseThrow(
                () -> new ApiException(NOT_FRIEND_REQUEST)
        );

        Member memberId = memberRepository.findById(member.getId()).orElseThrow(
                () -> new ApiException(NOT_FOUND_USER)
        );
        Member friendId = memberRepository.findById(friendAccept.getMember().getId()).orElseThrow(
                () -> new ApiException(NOT_FOUND_USER)
        );

        friendAccept.updateFriendStatus(ACCEPTED);

        friendRepository.save(Friend.of(memberId, friendId, ACCEPTED));
        return MessageDto.of("친구추가 성공", HttpStatus.ACCEPTED);
    }

    public List<FriendResponseDto> getFriendList(Member member) {

        List<Friend> friendList = friendRepository.findByMemberIdAndStatusOrderByFriendNicknameAsc(member.getId(), ACCEPTED);
        List<FriendResponseDto> friendResponseDtoList = new ArrayList<>();

        for (Friend friend : friendList) {
            friendResponseDtoList.add(FriendResponseDto
                    .of(friend.getId(), friend.getMember().getNickname(), friend.getFriend().getNickname(), friend.getFriend().getProfileImageUrl()));
        }
        return friendResponseDtoList;
    }

    @Transactional
    public MessageDto deleteFriendList(Long id, Member member) {

        Friend friend = friendRepository.findById(id).orElseThrow(
                () -> new ApiException(NOT_FOUND_USER)
        );

        if (!friend.getMember().getId().equals(member.getId())) {
            throw new ApiException(NOT_MATCH_AUTHORIZATION);
        }

        friendRepository.deleteByMemberIdAndFriendId(friend.getMember().getId(), friend.getFriend().getId());
        friendRepository.deleteByFriendIdAndMemberId(friend.getMember().getId(), friend.getFriend().getId());

        return MessageDto.of("친구 삭제 완료", HttpStatus.OK);
    }

}
