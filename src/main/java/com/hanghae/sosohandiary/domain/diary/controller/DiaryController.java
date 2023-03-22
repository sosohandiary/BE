package com.hanghae.sosohandiary.domain.diary.controller;

import com.hanghae.sosohandiary.domain.diary.dto.DiaryRequestDto;
import com.hanghae.sosohandiary.domain.diary.dto.DiaryResponseDto;
import com.hanghae.sosohandiary.domain.diary.service.DiaryService;
import com.hanghae.sosohandiary.security.MemberDetailsImpl;
import com.hanghae.sosohandiary.utils.MessageDto;
import com.hanghae.sosohandiary.utils.page.PageCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping("/diary")
    public DiaryResponseDto diarySave(@RequestPart(value = "data") DiaryRequestDto diaryDetailRequestDto,
                                      @RequestPart(value = "img", required = false) List<MultipartFile> multipartFileList,
                                      @AuthenticationPrincipal MemberDetailsImpl memberDetails) throws IOException {
        return diaryService.saveDiary(diaryDetailRequestDto, multipartFileList, memberDetails.getMember());
    }

    @GetMapping("/")
    public PageCustom<DiaryResponseDto> diaryList(@PageableDefault(size = 5) Pageable pageable) {
        return diaryService.findDiaryList(pageable);
    }

    @PatchMapping("/diary/{diary-id}")
    public DiaryResponseDto diaryModify(@PathVariable(name = "diary-id") Long id,
                                        @RequestPart(value = "title") DiaryRequestDto diaryRequestDto,
                                        @RequestPart(value = "img") List<MultipartFile> multipartFileList,
                                        @AuthenticationPrincipal MemberDetailsImpl memberDetails) throws IOException {
        return diaryService.modifyDiary(id, diaryRequestDto, multipartFileList, memberDetails.getMember());
    }

    @DeleteMapping("/diary/{diary-id}")
    public MessageDto diaryRemove(@PathVariable(name = "diary-id") Long id,
                                  @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return diaryService.removeDiary(id, memberDetails.getMember());
    }

}
