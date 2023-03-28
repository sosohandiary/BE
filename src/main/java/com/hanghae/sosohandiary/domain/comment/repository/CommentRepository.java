package com.hanghae.sosohandiary.domain.comment.repository;

import com.hanghae.sosohandiary.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByOrderByCreatedAtDesc();

    List<Comment> findByDiaryDetailIdOrderByModifiedAtDesc(Long id);

    int countCommentsByDiaryDetailId(Long id);

    void deleteAllByDiaryDetailId(Long detailId);
}
