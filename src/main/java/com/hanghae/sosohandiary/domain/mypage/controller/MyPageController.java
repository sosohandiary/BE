package com.hanghae.sosohandiary.domain.mypage.controller;

import com.hanghae.sosohandiary.domain.diary.dto.DiaryResponseDto;
import com.hanghae.sosohandiary.domain.member.dto.MemberResponseDto;
import com.hanghae.sosohandiary.domain.mypage.dto.MyPageDiaryResponseDto;
import com.hanghae.sosohandiary.domain.mypage.dto.MyPageFriendResponseDto;
import com.hanghae.sosohandiary.domain.mypage.dto.MyPageProfileResponseDto;
import com.hanghae.sosohandiary.domain.mypage.dto.ProfileEditRequestDto;
import com.hanghae.sosohandiary.domain.mypage.service.MyPageService;
import com.hanghae.sosohandiary.security.MemberDetailsImpl;
import com.hanghae.sosohandiary.utils.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {

    private final MyPageService mypageService;

    @GetMapping("/profile")
    public MyPageProfileResponseDto getMyPageProfile(@AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return mypageService.getProfile(memberDetails.getMember());
    }

    @PatchMapping("/profile/edit")
    public MessageDto editMyPageProfile(@AuthenticationPrincipal MemberDetailsImpl memberDetails,
                                        @RequestPart(value = "img", required = false) List<MultipartFile> multipartFileList,
                                        @RequestPart(value = "data") ProfileEditRequestDto profileEditRequestDto) throws IOException {
        return mypageService.editProfile(memberDetails.getMember(), multipartFileList, profileEditRequestDto);
    }

    @DeleteMapping("/out")
    public MessageDto deleteMember(@AuthenticationPrincipal MemberDetailsImpl memberDetails) throws IOException {
        return mypageService.deleteMember(memberDetails.getMember());
    }

    @GetMapping("/diary/count")
    public MyPageDiaryResponseDto getMyDiaryCount(@AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return mypageService.getMyDiaryCount(memberDetails.getMember());
    }

    @GetMapping("/friend/count")
    public MyPageFriendResponseDto getMyFriendCount(@AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return mypageService.getMyFriendCount(memberDetails.getMember());
    }

    @GetMapping("/diaries")
    public List<DiaryResponseDto> getMyDiaries(@AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return mypageService.getMyDiaries(memberDetails.getMember());
    }

    @GetMapping("/friend/myfriends")
    public List<MemberResponseDto> getMyFriends(@AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return mypageService.getMyFriends(memberDetails.getMember());
    }

}
