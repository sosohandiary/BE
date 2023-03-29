package com.hanghae.sosohandiary.domain.diarydetail.repository;

import com.hanghae.sosohandiary.domain.diarydetail.entity.DiaryDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiaryDetailRepository extends JpaRepository<DiaryDetail, Long> {

    void deleteAllByDiaryId(Long id);

    Page<DiaryDetail> findAllByDiaryIdOrderByModifiedAtDesc(Pageable pageable, Long id);

    Optional<DiaryDetail> findByDiaryIdAndId(Long diaryId, Long id);
}
