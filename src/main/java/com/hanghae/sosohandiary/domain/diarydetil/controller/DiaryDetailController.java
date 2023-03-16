package com.hanghae.sosohandiary.domain.diarydetil.controller;

import com.hanghae.sosohandiary.domain.diary.dto.DiaryRequestDto;
import com.hanghae.sosohandiary.domain.diarydetil.dto.DiaryDetailRequestDto;
import com.hanghae.sosohandiary.domain.diarydetil.dto.DiaryDetailResponseDto;
import com.hanghae.sosohandiary.domain.diarydetil.service.DiaryDetailService;
import com.hanghae.sosohandiary.security.MemberDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/diary/{diary-id}")
@RequiredArgsConstructor
public class DiaryDetailController {

    private final DiaryDetailService diaryDetailService;

    @PostMapping("/detail")
    public DiaryDetailResponseDto detailSave(@PathVariable(name = "diary-id") Long id,
                                             @RequestPart(value = "content") DiaryDetailRequestDto diaryDetailRequestDto,
                                             @RequestPart(value = "img") List<MultipartFile> multipartFileList,
                                             @AuthenticationPrincipal MemberDetailsImpl memberDetails) throws IOException {
        return diaryDetailService.saveDetail(id, diaryDetailRequestDto, multipartFileList, memberDetails.getMember());
    }
}
