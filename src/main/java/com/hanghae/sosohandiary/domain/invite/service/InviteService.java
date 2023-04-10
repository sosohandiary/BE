package com.hanghae.sosohandiary.domain.invite.service;

import com.hanghae.sosohandiary.domain.diary.entity.Diary;
import com.hanghae.sosohandiary.domain.diary.repository.DiaryRepository;
import com.hanghae.sosohandiary.domain.friend.entity.Friend;
import com.hanghae.sosohandiary.domain.friend.repository.FriendRepository;
import com.hanghae.sosohandiary.domain.invite.dto.InviteResponseDto;
import com.hanghae.sosohandiary.domain.invite.entity.Invite;
import com.hanghae.sosohandiary.domain.invite.repository.InviteRepository;
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

import static com.hanghae.sosohandiary.exception.ErrorHandling.*;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InviteService {

    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;
    private final InviteRepository inviteRepository;
    private final FriendRepository friendListRepository;

    @Transactional
    public MessageDto invite(Long diaryId, Member fromMember, Long toMemberId) {

        Diary diary = diaryRepository.findById(diaryId).orElseThrow(
                () -> new ApiException(NOT_FOUND_DIARY)
        );

        Member toMember = memberRepository.findById(toMemberId).orElseThrow(
                () -> new ApiException(NOT_FOUND_USER)
        );

        List<Friend> toFriend = friendListRepository.findAllByMemberId(fromMember.getId());

        if (inviteRepository.countByDiaryId(diaryId) >= 7) {
            throw new ApiException(OVER_THE_LIMIT);
        }

        if (toFriend.stream().noneMatch(friend -> friend.getFriend().getId().equals(toMemberId))) {
            throw new ApiException(NOT_FRIEND);
        }

        Invite invite = Invite.of(fromMember, toMember, diary);
        inviteRepository.save(invite);

        return MessageDto.of("공유 다이어리 초대 전송 완료", HttpStatus.OK);
    }

    @Transactional
    public MessageDto deny(Long inviteId) {

        Invite deleteInvite = inviteRepository.findById(inviteId).orElseThrow(
                () -> new ApiException(NOT_FOUND_INVITE)
        );

        inviteRepository.delete(deleteInvite);

        return MessageDto.of("공유 다이어리 초대 취소 하였습니다.", HttpStatus.ACCEPTED);
    }

    public List<InviteResponseDto> findInviteList(Long diaryId, Member member) {

        List<Invite> inviteList = inviteRepository.findAllByDiaryId(diaryId);
        List<InviteResponseDto> inviteResponseDtoList = new ArrayList<>();
        for (Invite invite : inviteList) {
            if (!(invite.getToMember().getId().equals(member.getId()) || invite.getFromMember().getId().equals(member.getId()))) {
                throw new ApiException(NOT_MATCH_AUTHORIZATION);
            }
            inviteResponseDtoList.add(InviteResponseDto.of(invite, invite.getToMember()));
        }

        return inviteResponseDtoList;
    }

    public List<InviteResponseDto> alarmInvite(Member member) {

        List<Invite> inviteList = inviteRepository.findAllByToMemberId(member.getId());
        List<InviteResponseDto> inviteResponseDtoList = new ArrayList<>();
        for (Invite invite : inviteList) {
            if (invite.getToMember().getId().equals(member.getId())) {
                inviteResponseDtoList.add(InviteResponseDto.of(invite, invite.getFromMember()));
            }
        }

        return inviteResponseDtoList;
    }

    @Transactional
    public InviteResponseDto alarmRead(Long inviteId, Member member) {

        Invite invite = inviteRepository.findById(inviteId).orElseThrow(
                () -> new ApiException(NOT_FOUND_INVITE)
        );

        if (!invite.getToMember().getId().equals(member.getId())) {
            throw new ApiException(NOT_MATCH_AUTHORIZATION);
        }

        invite.updateAlarm(true);
        return InviteResponseDto.of(invite, invite.getToMember());
    }
}
