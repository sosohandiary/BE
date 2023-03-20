package com.hanghae.sosohandiary.domain.diarydetil.controller;

import com.hanghae.sosohandiary.domain.diarydetil.dto.DiaryDetailRequestDto;
import com.hanghae.sosohandiary.domain.diarydetil.dto.DiaryDetailResponseDto;
import com.hanghae.sosohandiary.domain.diarydetil.service.DiaryDetailService;
import com.hanghae.sosohandiary.security.MemberDetailsImpl;
import com.hanghae.sosohandiary.utils.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping("/detail")
    public Page<DiaryDetailResponseDto> detailFindList(@PathVariable(name = "diary-id") Long id,
                                                       @PageableDefault(size = 5) Pageable pageable) {
        return diaryDetailService.findListDetail(id, pageable);
    }

    @GetMapping("/detail/{detail-id}")
    public DiaryDetailResponseDto detailFind(@PathVariable(name = "diary-id") Long diaryId,
                                             @PathVariable(name = "detail-id") Long detailId) {
        return diaryDetailService.findDetail(diaryId, detailId);
    }

    @PatchMapping("/detail/{detail-id}")
    public DiaryDetailResponseDto detailModify(@PathVariable(name = "diary-id") Long diaryId,
                                               @PathVariable(name = "detail-id") Long detailId,
                                               @RequestPart(value = "content") DiaryDetailRequestDto diaryDetailRequestDto,
                                               @RequestPart(value = "img") List<MultipartFile> multipartFileList,
                                               @AuthenticationPrincipal MemberDetailsImpl memberDetails) throws IOException {
        return diaryDetailService.modifyDetail(diaryId, detailId, diaryDetailRequestDto, multipartFileList, memberDetails.getMember());
    }

    @DeleteMapping("/detail/{detail-id}")
    public MessageDto detailRemove(@PathVariable(name = "diary-id") Long diaryId,
                                   @PathVariable(name = "detail-id") Long detailId,
                                   @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return diaryDetailService.removeDetail(diaryId, detailId, memberDetails.getMember());
    }
}
