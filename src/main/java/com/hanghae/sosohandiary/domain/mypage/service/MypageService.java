package com.hanghae.sosohandiary.domain.mypage.service;

import com.hanghae.sosohandiary.domain.diary.entity.Diary;
import com.hanghae.sosohandiary.domain.diary.repository.DiaryRepository;
import com.hanghae.sosohandiary.domain.diary.service.DiaryService;
import com.hanghae.sosohandiary.domain.member.entity.Member;
import com.hanghae.sosohandiary.domain.member.repository.MemberRepository;
import com.hanghae.sosohandiary.domain.friend.repository.FriendListRepository;
import com.hanghae.sosohandiary.domain.mypage.dto.MypageDiaryResponseDto;
import com.hanghae.sosohandiary.domain.mypage.dto.MypageFriendResponseDto;
import com.hanghae.sosohandiary.domain.mypage.dto.MypageProfileResponseDto;
import com.hanghae.sosohandiary.domain.mypage.dto.ProfileEditRequestDto;
import com.hanghae.sosohandiary.exception.ApiException;
import com.hanghae.sosohandiary.utils.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.hanghae.sosohandiary.exception.ErrorHandling.NOT_FOUND_USER;

@Service
@RequiredArgsConstructor
public class MypageService {

    private final MemberRepository memberRepository;
    private final DiaryService diaryService;
    private final DiaryRepository diaryRepository;
    private final FriendListRepository friendsRepository;

    @Transactional(readOnly = true)
    public MypageProfileResponseDto getProfile(Member member) {

        member = memberRepository.findById(member.getId()).orElseThrow(
                () -> new ApiException(NOT_FOUND_USER)
        );

        String nickname = member.getNickname();
        String statusMessage = member.getStatusMessage();

        return MypageProfileResponseDto.of(nickname, statusMessage);
    }

    @Transactional
    public MessageDto editProfile(Member member, ProfileEditRequestDto profileEditRequestDto) {

        member = memberRepository.findById(member.getId()).orElseThrow(
                () -> new ApiException(NOT_FOUND_USER)
        );

        member.updateProfile(profileEditRequestDto.getNickname(), profileEditRequestDto.getStatusMessage());

        memberRepository.save(member);

        return MessageDto.of("프로필 수정 성공", HttpStatus.ACCEPTED);

    }

    @Transactional
    public MessageDto deleteMember(Member member) {

        member = memberRepository.findById(member.getId()).orElseThrow(
                () -> new ApiException(NOT_FOUND_USER)
        );

        Optional<Diary> diary = diaryRepository.findByMemberId(member.getId());
        if (diary.isPresent()) {
            diaryService.removeDiary(diary.get().getId(), member);
        }

        memberRepository.deleteById(member.getId());

        return MessageDto.of("회원 탈퇴 성공", HttpStatus.ACCEPTED);
    }

    @Transactional(readOnly = true)
    public MypageDiaryResponseDto getMyDiaryCount(Member member) {

        member = memberRepository.findById(member.getId()).orElseThrow(
                () -> new ApiException(NOT_FOUND_USER)
        );

        Long diaryCount = diaryRepository.countByMemberId(member.getId());

        return MypageDiaryResponseDto.from(diaryCount);
    }

    @Transactional(readOnly = true)
    public MypageFriendResponseDto getMyFriendCount(Member member) {

        member = memberRepository.findById(member.getId()).orElseThrow(
                () -> new ApiException(NOT_FOUND_USER)
        );

        Long friendCount = friendsRepository.countAllByMemberId(member.getId());

        return MypageFriendResponseDto.from(friendCount);
    }
}
