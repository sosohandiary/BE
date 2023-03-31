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
@RequestMapping("/diary/{diary-id}/detail")
@RequiredArgsConstructor
public class DiaryDetailController {

    private final DiaryDetailService diaryDetailService;

    @PostMapping
    public DiaryDetailResponseDto createDetail(@PathVariable("diary-id") Long id,
                                               @RequestBody DiaryDetailRequestDto diaryDetailRequestDto,
                                               @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return diaryDetailService.createDetail(id, diaryDetailRequestDto, memberDetails.getMember());
    }

    @GetMapping
    public PageCustom<DiaryDetailResponseDto> findDetailList(@PathVariable("diary-id") Long id,
                                                             @PageableDefault(size = 5) Pageable pageable) {
        return diaryDetailService.findListDetail(id, pageable);
    }

    @GetMapping("/{detail-id}")
    public DiaryDetailResponseDto findDetail(@PathVariable("diary-id") Long diaryId,
                                             @PathVariable("detail-id") Long detailId,
                                             @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return diaryDetailService.findDetail(diaryId, detailId, memberDetails.getMember());
    }

    @PatchMapping("/{detail-id}")
    public DiaryDetailResponseDto modifyDetail(@PathVariable("diary-id") Long diaryId,
                                               @PathVariable("detail-id") Long detailId,
                                               @RequestBody DiaryDetailRequestDto diaryDetailRequestDto,
                                               @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return diaryDetailService.modifyDetail(diaryId, detailId, diaryDetailRequestDto, memberDetails.getMember());
    }

    @DeleteMapping("/{detail-id}")
    public MessageDto removeDetail(@PathVariable("diary-id") Long diaryId,
                                   @PathVariable("detail-id") Long detailId,
                                   @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return diaryDetailService.removeDetail(diaryId, detailId, memberDetails.getMember());
    }
}
