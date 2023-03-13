package com.hanghae.sosohandiary.domain.diary.service;

import com.hanghae.sosohandiary.domain.diary.dto.DiaryRequestDto;
import com.hanghae.sosohandiary.domain.diary.dto.DiaryResponseDto;
import com.hanghae.sosohandiary.domain.diary.entity.Diary;
import com.hanghae.sosohandiary.domain.diary.repository.DiaryRepository;
import com.hanghae.sosohandiary.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiaryService {

    private final DiaryRepository diaryRepository;

    @Transactional
    public DiaryResponseDto saveDiary(DiaryRequestDto diaryRequestDto, Member member) {

        return DiaryResponseDto.from(diaryRepository.save(Diary.of(diaryRequestDto, member)), member);
    }


}
