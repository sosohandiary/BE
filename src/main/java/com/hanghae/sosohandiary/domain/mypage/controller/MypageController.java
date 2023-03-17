package com.hanghae.sosohandiary.domain.mypage.controller;

import com.hanghae.sosohandiary.domain.mypage.dto.MypageResponseDto;
import com.hanghae.sosohandiary.domain.mypage.dto.ProfileEditRequestDto;
import com.hanghae.sosohandiary.domain.mypage.service.MypageService;
import com.hanghae.sosohandiary.security.MemberDetailsImpl;
import com.hanghae.sosohandiary.utils.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MypageController {

    private final MypageService mypageService;

    @GetMapping("/profile")
    public MypageResponseDto getMypageProfile(@AuthenticationPrincipal MemberDetailsImpl memberDetails) {
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


}
