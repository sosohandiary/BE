package com.hanghae.sosohandiary.domain.diary.repository;

import com.hanghae.sosohandiary.domain.diary.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

}
