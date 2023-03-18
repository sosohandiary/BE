package com.hanghae.sosohandiary.domain.diary.repository;

import com.hanghae.sosohandiary.domain.diary.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    Optional<List<Diary>> findAllByOrderByModifiedAtDesc();

    Optional<List<Diary>> findAllById(Long id);
    void deleteById(Long id);

    Optional<Diary> findByMemberId(Long id);

    Long countByMemberId(Long id);
}
