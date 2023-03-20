package com.hanghae.sosohandiary.domain.diary.repository;

import com.hanghae.sosohandiary.domain.diary.entity.Diary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    Page<Diary> findAllByOrderByModifiedAtDesc(Pageable pageable);

    void deleteById(Long id);

    Optional<Diary> findByMemberId(Long id);

    Long countByMemberId(Long id);

    List<Diary> findAllByMemberIdOrderByModifiedAtDesc(Long id);
}
