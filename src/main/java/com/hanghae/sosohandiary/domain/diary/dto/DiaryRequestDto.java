package com.hanghae.sosohandiary.domain.diary.dto;

import com.hanghae.sosohandiary.domain.diary.entity.DiaryCondition;
import lombok.Getter;

@Getter
public class DiaryRequestDto {

    private String title;
    private DiaryCondition diaryCondition;

}
