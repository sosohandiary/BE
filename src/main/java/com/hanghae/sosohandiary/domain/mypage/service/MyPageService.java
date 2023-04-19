package com.hanghae.sosohandiary.domain.mypage.service;

import com.hanghae.sosohandiary.domain.diary.dto.DiaryResponseDto;
import com.hanghae.sosohandiary.domain.diary.entity.Diary;
import com.hanghae.sosohandiary.domain.diary.repository.DiaryRepository;
import com.hanghae.sosohandiary.domain.diary.service.DiaryService;
import com.hanghae.sosohandiary.domain.friend.entity.Friend;
import com.hanghae.sosohandiary.domain.friend.entity.StatusFriend;
import com.hanghae.sosohandiary.domain.friend.repository.FriendRepository;
import com.hanghae.sosohandiary.domain.invite.repository.InviteRepository;
import com.hanghae.sosohandiary.domain.member.dto.MemberResponseDto;
import com.hanghae.sosohandiary.domain.member.entity.Member;
import com.hanghae.sosohandiary.domain.member.repository.MemberRepository;
import com.hanghae.sosohandiary.domain.mypage.dto.MyPageDiaryResponseDto;
import com.hanghae.sosohandiary.domain.mypage.dto.MyPageFriendResponseDto;
import com.hanghae.sosohandiary.domain.mypage.dto.MyPageProfileResponseDto;
import com.hanghae.sosohandiary.domain.mypage.dto.ProfileEditRequestDto;
import com.hanghae.sosohandiary.exception.ApiException;
import com.hanghae.sosohandiary.utils.MessageDto;
import com.hanghae.sosohandiary.utils.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.hanghae.sosohandiary.exception.ErrorHandling.NOT_FOUND_USER;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyPageService {

    private final MemberRepository memberRepository;
    private final DiaryService diaryService;
    private final DiaryRepository diaryRepository;
    private final FriendRepository friendsRepository;
    private final InviteRepository inviteRepository;
    private final S3Service s3Service;

    public MyPageProfileResponseDto getProfile(Member member) {

        Member foundMember = memberRepository.findById(member.getId()).orElseThrow(
                () -> new ApiException(NOT_FOUND_USER)
        );

        return MyPageProfileResponseDto.of(foundMember);
    }

    @Transactional
    public MessageDto editProfile(Member member, List<MultipartFile> multipartFileList, ProfileEditRequestDto profileEditRequestDto) throws IOException {

        Member foundMember = memberRepository.findById(member.getId()).orElseThrow(
                () -> new ApiException(NOT_FOUND_USER)
        );


        if (multipartFileList == null) {
            foundMember.updateProfile(profileEditRequestDto.getNickname(), profileEditRequestDto.getStatusMessage());
        } else {
            s3Service.uploadProfileImage(multipartFileList);
            foundMember.updateProfile(s3Service.getUploadImageUrl(), profileEditRequestDto.getNickname(), profileEditRequestDto.getStatusMessage());
        }

        return MessageDto.of("프로필 수정 성공", HttpStatus.ACCEPTED);

    }

    @Transactional
    public MessageDto deleteMember(Member member) throws IOException {

        member = memberRepository.findById(member.getId()).orElseThrow(
                () -> new ApiException(NOT_FOUND_USER)
        );

        List<Diary> diaryList = diaryRepository.findByMemberId(member.getId());

        for (Diary diary : diaryList) {

            if (diaryList.isEmpty()) {
                diaryService.removeDiary(diary.getId(), member);
            }
            diaryService.removeDiary(diary.getId(), member);
        }

        memberRepository.deleteById(member.getId());
        diaryRepository.deleteAllByMemberId(member.getId());
        friendsRepository.deleteAllByFriendId(member.getId());
        inviteRepository.deleteAllByToMemberId(member.getId());

        return MessageDto.of("회원 탈퇴 성공", HttpStatus.ACCEPTED);
    }

    public MyPageDiaryResponseDto getMyDiaryCount(Member member) {

        Member foundMember = memberRepository.findById(member.getId()).orElseThrow(
                () -> new ApiException(NOT_FOUND_USER)
        );

        Long diaryCount = diaryRepository.countByMemberId(foundMember.getId());

        return MyPageDiaryResponseDto.from(diaryCount);
    }

    public MyPageFriendResponseDto getMyFriendCount(Member member) {

        Member foundMember = memberRepository.findById(member.getId()).orElseThrow(
                () -> new ApiException(NOT_FOUND_USER)
        );

        Long friendCount = friendsRepository.countAllByMemberIdAndStatus(foundMember.getId(), StatusFriend.ACCEPTED);

        return MyPageFriendResponseDto.from(friendCount);
    }

    public List<DiaryResponseDto> getMyDiaries(Member member) {

        Member foundMember = memberRepository.findById(member.getId()).orElseThrow(
                () -> new ApiException(NOT_FOUND_USER)
        );

        List<Diary> diaryList = diaryRepository.findAllByMemberIdOrderByModifiedAtDesc(foundMember.getId());

        List<DiaryResponseDto> diaryResponseDtoList = new ArrayList<>();

        for (Diary diary : diaryList) {
            diaryResponseDtoList.add(DiaryResponseDto.of(diary, foundMember));
        }

        return diaryResponseDtoList;
    }

    public List<MemberResponseDto> getMyFriends(Member member) {

        Member foundMember = memberRepository.findById(member.getId()).orElseThrow(
                () -> new ApiException(NOT_FOUND_USER)
        );

        List<Friend> friendList = friendsRepository.findAllByMemberId(foundMember.getId());

        List<MemberResponseDto> getMyFriends = new ArrayList<>();

        for (Friend friend : friendList) {
            getMyFriends.add(MemberResponseDto.from(friend));
        }

        return getMyFriends;
    }
}
