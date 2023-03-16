package com.hanghae.sosohandiary.domain.image.repository;

import com.hanghae.sosohandiary.domain.diarydetil.entity.DiaryDetail;
import com.hanghae.sosohandiary.domain.image.entity.DiaryDetailImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiaryDetailImageRepository extends JpaRepository<DiaryDetailImage, Long> {

    List<DiaryDetailImage> findAllByDiaryDetail(DiaryDetail diaryDetail);

    void deleteAllByDiaryDetailId(Long id);
    void deleteById(Long id);
}
