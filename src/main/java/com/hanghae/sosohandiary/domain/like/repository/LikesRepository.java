package com.hanghae.sosohandiary.domain.like.repository;

import com.hanghae.sosohandiary.domain.like.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    int countByDiaryIdAndDiaryDetailId(Long diaryId, Long diaryDetailId);

    Optional<Likes> findByDiaryIdAndDiaryDetailIdAndMemberId(Long diaryId, Long detailId, Long id);
}
