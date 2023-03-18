package com.hanghae.sosohandiary.domain.diarydetil.repository;

import com.hanghae.sosohandiary.domain.diarydetil.entity.DiaryDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiaryDetailRepository extends JpaRepository<DiaryDetail, Long> {

    Optional<List<DiaryDetail>> findAllByOrderByModifiedAtDesc();

    Optional<List<DiaryDetail>> findAllByDiaryId(Long id);

    Optional<List<DiaryDetail>> findAllById(Long id);

    void deleteAllByDiaryId(Long id);

}
