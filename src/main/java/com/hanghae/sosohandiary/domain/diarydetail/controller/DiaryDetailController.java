package com.hanghae.sosohandiary.domain.diarydetail.controller;

import com.hanghae.sosohandiary.domain.diarydetail.dto.DiaryDetailRequestDto;
import com.hanghae.sosohandiary.domain.diarydetail.dto.DiaryDetailResponseDto;
import com.hanghae.sosohandiary.domain.diarydetail.service.DiaryDetailService;
import com.hanghae.sosohandiary.security.MemberDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class DiaryDetailController {

    private final DiaryDetailService diaryService;

    // TODO: 2023-03-13 controller 회원 로직 완료 후 member 추가
    @PostMapping("/diary")
    public DiaryDetailResponseDto diarySave(@RequestPart(value = "data") DiaryDetailRequestDto diaryDetailRequestDto,
                                            @RequestPart(value = "file") List<MultipartFile> multipartFileList,
                                            @AuthenticationPrincipal MemberDetailsImpl memberDetails) throws IOException {
        return diaryService.saveDiary(diaryDetailRequestDto, multipartFileList, memberDetails.getMember());
    }

    // TODO: 2023-03-13 controller 회원 로직 완료 후 member 추가
    @GetMapping("/")
    public List<DiaryDetailResponseDto> diaryList() {
        return diaryService.findDiaryList();
    }

}
