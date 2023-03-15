package com.hanghae.sosohandiary.domain.image.repository;

import com.hanghae.sosohandiary.domain.diary.entity.Diary;
import com.hanghae.sosohandiary.domain.image.entity.DiaryImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiaryImageRepository extends JpaRepository<DiaryImage, Long> {

    List<DiaryImage> findAllByDiary(Diary diary);

    void deleteAllByDiaryId(Long id);

}
