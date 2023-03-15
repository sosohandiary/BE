package com.hanghae.sosohandiary.domain.image.repository;

import com.hanghae.sosohandiary.domain.diarydetail.entity.DiaryDetail;
import com.hanghae.sosohandiary.domain.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findAllByDiary(DiaryDetail diary);
//
//    void deleteAllByQuestionBoardId(Long id);

}
