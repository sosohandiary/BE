package com.hanghae.sosohandiary.domain.comment.repository;

import com.hanghae.sosohandiary.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
