package com.hanghae.sosohandiary.domain.friend.service;

import com.hanghae.sosohandiary.domain.friend.dto.FriendResponseDto;
import com.hanghae.sosohandiary.domain.friend.entity.Enum.StatusFriend;
import com.hanghae.sosohandiary.domain.friend.entity.Friend;
import com.hanghae.sosohandiary.domain.friend.repository.FriendRepository;
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
public class FriendService {
    private final FriendRepository friendRepository;
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
        boolean isDuplicated = friendRepository.existsByFriend_IdAndMember_Id(id, memberId.getId());
        if (isDuplicated) {
            return new MessageDto("이미 상대방에게 친구 요청 하였습니다.", HttpStatus.BAD_REQUEST);
        }

        friendRepository.save(Friend.of(memberId, friendId, StatusFriend.PENDING));

        return new MessageDto("친구요청 성공", HttpStatus.CREATED);

    }

    public List<FriendResponseDto> getFriendRequest(Member member) {
        List<Friend> friendList = friendRepository.findByFriendIdAndStatusOrderByCreatedAtDesc(member.getId(), StatusFriend.PENDING);
        List<FriendResponseDto> friendResponseDtoList = new ArrayList<>();

        for (Friend friend : friendList) {
            friendResponseDtoList.add(FriendResponseDto.builder()
                    .id(friend.getId())
                    .friendNickName(friend.getMember().getNickname())
                    .nickname(friend.getFriend().getNickname())
                    .build());
        }
        return friendResponseDtoList;
    }

    @Transactional
    public MessageDto acceptFriend(Long id, Member member) {

        Friend friendAccept = friendRepository.findByIdAndFriendId(id, member.getId()).orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_REQUEST)
        );

        Member memberId = memberRepository.findById(member.getId()).orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_FOUND_USER)
        );
        Member friendId = memberRepository.findById(friendAccept.getMember().getId()).orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_FOUND_USER)
        );

        friendAccept.updateFriendStatus(StatusFriend.ACCEPTED);

        friendRepository.save(Friend.of(memberId, friendId, StatusFriend.ACCEPTED));
        return new MessageDto("친구추가 성공", HttpStatus.ACCEPTED);
    }

    public List<FriendResponseDto> getFriendList(Member member) {
        List<Friend> friendList = friendRepository.findByMemberIdAndStatusOrderByFriendNicknameAsc(member.getId(), StatusFriend.ACCEPTED);
        List<FriendResponseDto> friendResponseDtoList = new ArrayList<>();

        for (Friend friend : friendList) {
            friendResponseDtoList.add(FriendResponseDto.builder()
                    .id(friend.getId())
                    .friendNickName(friend.getFriend().getNickname())
                    .nickname(friend.getMember().getNickname())
                    .build());
        }
        return friendResponseDtoList;
    }

    @Transactional
    public MessageDto deleteFriendList(Long id, Member member) {
        Friend friend = friendRepository.findById(id).orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_FOUND_USER)
        );

        if (!friend.getMember().getId().equals(member.getId())) {
            throw new ApiException(ErrorHandling.NOT_MATCH_AUTHORIZATION);
        }

        friendRepository.deleteByMemberIdAndFriendId(friend.getMember().getId(), friend.getFriend().getId());
        friendRepository.deleteByFriendIdAndMemberId(friend.getMember().getId(), friend.getFriend().getId());
        return MessageDto.of("친구 삭제 완료", HttpStatus.OK);
    }

}
