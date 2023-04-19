package com.hanghae.sosohandiary.domain.like.repository;

import com.hanghae.sosohandiary.domain.like.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    Likes findByDiaryDetailIdAndMemberId(Long detailId, Long id);

    int countByDiaryDetailId(Long id);

    void deleteAllByDiaryDetailId(Long detailId);

    List<Likes> findAllByDiaryDetailId(Long detailId);

    boolean existsByDiaryDetailIdAndMemberId(Long detailId, Long id);
}
