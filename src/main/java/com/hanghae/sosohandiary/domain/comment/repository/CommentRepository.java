package com.hanghae.sosohandiary.domain.comment.repository;

import com.hanghae.sosohandiary.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByDiaryDetailIdOrderByCreatedAtDesc(Long detailId);

    Optional<Comment> findByDiaryDetailIdOrderByModifiedAtDesc(Long memberId);

    int countCommentsByDiaryDetailId(Long id);

    void deleteAllByDiaryDetailId(Long detailId);
}
