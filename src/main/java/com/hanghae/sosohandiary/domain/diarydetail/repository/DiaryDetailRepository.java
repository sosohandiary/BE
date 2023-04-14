package com.hanghae.sosohandiary.domain.diarydetail.repository;

import com.hanghae.sosohandiary.domain.diarydetail.entity.DiaryDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiaryDetailRepository extends JpaRepository<DiaryDetail, Long> {

    void deleteAllByDiaryId(Long id);

    Optional<DiaryDetail> findByDiaryIdAndId(Long diaryId, Long id);

    List<DiaryDetail> findAllByDiaryIdOrderByCreatedAtAsc(Long id);

    List<DiaryDetail> findAllByMemberId(Long id);

    List<DiaryDetail> findAllByDiaryId(Long id);
}
