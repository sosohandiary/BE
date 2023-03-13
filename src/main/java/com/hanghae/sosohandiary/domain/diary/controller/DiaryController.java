package com.hanghae.sosohandiary.domain.diary.controller;

import com.hanghae.sosohandiary.domain.diary.dto.DiaryRequestDto;
import com.hanghae.sosohandiary.domain.diary.dto.DiaryResponseDto;
import com.hanghae.sosohandiary.domain.diary.service.DiaryService;
import com.hanghae.sosohandiary.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    // TODO: 2023-03-13 controller 회원 로직 완료 후 member 추가
    @PostMapping("/diary")
    public DiaryResponseDto diarySave(@RequestBody DiaryRequestDto diaryRequestDto
                                      ) {
        return diaryService.saveDiary(diaryRequestDto);
    }

    // TODO: 2023-03-13 controller 회원 로직 완료 후 member 추가
    @GetMapping("/")
    public List<DiaryResponseDto> diaryList() {
        return diaryService.findDiaryList();
    }

}
