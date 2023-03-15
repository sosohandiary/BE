package com.hanghae.sosohandiary.domain.diarydetail.repository;

import com.hanghae.sosohandiary.domain.diarydetail.entity.DiaryDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiaryDetailRepository extends JpaRepository<DiaryDetail, Long> {
    Optional<List<DiaryDetail>> findAllByOrderByModifiedAtDesc();
}
