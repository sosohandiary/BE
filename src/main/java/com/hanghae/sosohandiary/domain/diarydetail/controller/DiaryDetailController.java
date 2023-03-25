package com.hanghae.sosohandiary.domain.diarydetail.controller;

import com.hanghae.sosohandiary.domain.diarydetail.dto.DiaryDetailRequestDto;
import com.hanghae.sosohandiary.domain.diarydetail.dto.DiaryDetailResponseDto;
import com.hanghae.sosohandiary.domain.diarydetail.service.DiaryDetailService;
import com.hanghae.sosohandiary.security.MemberDetailsImpl;
import com.hanghae.sosohandiary.utils.MessageDto;
import com.hanghae.sosohandiary.utils.page.PageCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/diary/{diary-id}")
@RequiredArgsConstructor
public class DiaryDetailController {

    private final DiaryDetailService diaryDetailService;

    @PostMapping("/detail")
    public DiaryDetailResponseDto detailSave(@PathVariable(name = "diary-id") Long id,
                                             @RequestBody DiaryDetailRequestDto diaryDetailRequestDto,
                                             @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return diaryDetailService.saveDetail(id, diaryDetailRequestDto, memberDetails.getMember());
    }

    @GetMapping("/detail")
    public PageCustom<DiaryDetailResponseDto> detailFindList(@PathVariable(name = "diary-id") Long id,
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
                                               @RequestBody DiaryDetailRequestDto diaryDetailRequestDto,
                                               @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return diaryDetailService.modifyDetail(diaryId, detailId, diaryDetailRequestDto, memberDetails.getMember());
    }

    @DeleteMapping("/detail/{detail-id}")
    public MessageDto detailRemove(@PathVariable(name = "diary-id") Long diaryId,
                                   @PathVariable(name = "detail-id") Long detailId,
                                   @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return diaryDetailService.removeDetail(diaryId, detailId, memberDetails.getMember());
    }
}
