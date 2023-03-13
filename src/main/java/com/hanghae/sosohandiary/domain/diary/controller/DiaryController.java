package com.hanghae.sosohandiary.domain.diary.controller;

import com.hanghae.sosohandiary.domain.diary.dto.DiaryRequestDto;
import com.hanghae.sosohandiary.domain.diary.dto.DiaryResponseDto;
import com.hanghae.sosohandiary.domain.diary.service.DiaryService;
import com.hanghae.sosohandiary.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping("/diary")
    public DiaryResponseDto diarySave(@RequestBody DiaryRequestDto diaryRequestDto,
                                      @RequestBody Member member) {
        return diaryService.saveDiary(diaryRequestDto, member);
    }



}
