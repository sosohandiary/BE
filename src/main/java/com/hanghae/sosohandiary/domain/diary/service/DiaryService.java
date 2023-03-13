package com.hanghae.sosohandiary.domain.diary.service;

import com.hanghae.sosohandiary.domain.diary.dto.DiaryRequestDto;
import com.hanghae.sosohandiary.domain.diary.dto.DiaryResponseDto;
import com.hanghae.sosohandiary.domain.diary.entity.Diary;
import com.hanghae.sosohandiary.domain.diary.repository.DiaryRepository;
import com.hanghae.sosohandiary.domain.member.entity.Member;
import com.hanghae.sosohandiary.exception.ApiException;
import com.hanghae.sosohandiary.exception.ErrorHandling;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiaryService {

    private final DiaryRepository diaryRepository;

    // TODO: 2023-03-13 service 회원 로직 완료 후 member 추가
    @Transactional
    public DiaryResponseDto saveDiary(DiaryRequestDto diaryRequestDto) {

        return DiaryResponseDto.from(diaryRepository.save(Diary.of(diaryRequestDto)));
    }

    public List<DiaryResponseDto> findDiaryList() {
        List<Diary> diaryList = diaryRepository.findAllByOrderByModifiedAtDesc().orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_FOUND_DIARY)
        );
        List<DiaryResponseDto> diaryResponseDtoList = new ArrayList<>();

        for (Diary diary : diaryList) {
            diaryResponseDtoList.add(DiaryResponseDto.from(diary));
        }

        return diaryResponseDtoList;
    }
}
