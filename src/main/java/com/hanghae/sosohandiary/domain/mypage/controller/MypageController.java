package com.hanghae.sosohandiary.domain.mypage.controller;

import com.hanghae.sosohandiary.domain.diary.dto.DiaryResponseDto;
import com.hanghae.sosohandiary.domain.mypage.dto.MypageDiaryResponseDto;
import com.hanghae.sosohandiary.domain.mypage.dto.MypageFriendResponseDto;
import com.hanghae.sosohandiary.domain.mypage.dto.MypageProfileResponseDto;
import com.hanghae.sosohandiary.domain.mypage.dto.ProfileEditRequestDto;
import com.hanghae.sosohandiary.domain.mypage.service.MypageService;
import com.hanghae.sosohandiary.security.MemberDetailsImpl;
import com.hanghae.sosohandiary.utils.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MypageController {

    private final MypageService mypageService;

    @GetMapping("/profile")
    public MypageProfileResponseDto getMypageProfile(@AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return mypageService.getProfile(memberDetails.getMember());
    }

    @PatchMapping("/profile/edit")
    public MessageDto editMypageProfile(@AuthenticationPrincipal MemberDetailsImpl memberDetails,
                                        @RequestBody ProfileEditRequestDto profileEditRequestDto) {
        return mypageService.editProfile(memberDetails.getMember(), profileEditRequestDto);
    }

    @DeleteMapping("/out")
    public MessageDto deleteMember(@AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return mypageService.deleteMember(memberDetails.getMember());
    }

    @GetMapping("/diary/count")
    public MypageDiaryResponseDto getMyDiaryCount(@AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return mypageService.getMyDiaryCount(memberDetails.getMember());
    }

    @GetMapping("/friend/count")
    public MypageFriendResponseDto getMyFriendCount(@AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return mypageService.getMyFriendCount(memberDetails.getMember());
    }

    @GetMapping("/diaries")
    public List<DiaryResponseDto> getMyDiaries(@AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return mypageService.getMyDiaries(memberDetails.getMember());
    }

}
