package com.hanghae.sosohandiary.domain.diarydetail.service;

import com.hanghae.sosohandiary.domain.comment.repository.CommentRepository;
import com.hanghae.sosohandiary.domain.diary.entity.Diary;
import com.hanghae.sosohandiary.domain.diary.repository.DiaryRepository;
import com.hanghae.sosohandiary.domain.diarydetail.dto.DiaryDetailRequestDto;
import com.hanghae.sosohandiary.domain.diarydetail.dto.DiaryDetailResponseDto;
import com.hanghae.sosohandiary.domain.diarydetail.entity.DiaryDetail;
import com.hanghae.sosohandiary.domain.diarydetail.repository.DiaryDetailRepository;
import com.hanghae.sosohandiary.domain.like.repository.LikesRepository;
import com.hanghae.sosohandiary.domain.member.entity.Member;
import com.hanghae.sosohandiary.exception.ApiException;
import com.hanghae.sosohandiary.exception.ErrorHandling;
import com.hanghae.sosohandiary.utils.MessageDto;
import com.hanghae.sosohandiary.utils.page.PageCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiaryDetailService {

    private final DiaryDetailRepository diaryDetailRepository;
    private final DiaryRepository diaryRepository;
    private final LikesRepository likesRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public DiaryDetailResponseDto saveDetail(Long id,
                                             DiaryDetailRequestDto diaryDetailRequestDto,
                                             Member member) {

        Diary diary = diaryRepository.findById(id).orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_FOUND_DIARY)
        );

        DiaryDetail diaryDetail = diaryDetailRepository.save(DiaryDetail.of(diaryDetailRequestDto, diary));

        return DiaryDetailResponseDto.from(diaryDetail, diary);
    }

    public PageCustom<DiaryDetailResponseDto> findListDetail(Long id, Pageable pageable) {
        int likes = 0;
        Diary diary = diaryRepository.findById(id).orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_FOUND_DIARY)
        );
        Page<DiaryDetailResponseDto> diaryDetailResponseDtoPage = diaryDetailRepository.findAllByOrderByModifiedAtDesc(pageable).map(
                (DiaryDetail diaryDetail) -> DiaryDetailResponseDto.from(diaryDetail, diary,likesRepository.countByDiaryDetailId(diaryDetail.getId()),commentRepository.countCommentsByDiaryDetailId(diaryDetail.getId()))
        );

        return new PageCustom<>(diaryDetailResponseDtoPage.getContent(), diaryDetailResponseDtoPage.getPageable(), diaryDetailResponseDtoPage.getTotalElements());
    }

    public DiaryDetailResponseDto findDetail(Long diaryId, Long detailId) {
        Diary diary = diaryRepository.findById(diaryId).orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_FOUND_DIARY)
        );

        DiaryDetail diaryDetail = diaryDetailRepository.findById(detailId).orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_FOUND_DIARY)
        );

        return DiaryDetailResponseDto.from(diaryDetail, diary, likesRepository.countByDiaryDetailId(detailId),commentRepository.countCommentsByDiaryDetailId(detailId));
    }

    @Transactional
    public DiaryDetailResponseDto modifyDetail(Long diaryId,
                                               Long detailId,
                                               DiaryDetailRequestDto diaryDetailRequestDto,
                                               Member member) {
        Diary diary = diaryRepository.findById(diaryId).orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_FOUND_DIARY)
        );

        DiaryDetail diaryDetail = diaryDetailRepository.findById(detailId).orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_FOUND_DIARY)
        );

        diaryDetail.update(diaryDetailRequestDto);

        return DiaryDetailResponseDto.from(diaryDetail, diary);
    }

    @Transactional
    public MessageDto removeDetail(Long diaryId, Long detailId, Member member) {
        diaryRepository.findById(diaryId).orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_FOUND_DIARY)
        );

        diaryDetailRepository.findById(detailId).orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_FOUND_DIARY)
        );

        diaryDetailRepository.deleteById(detailId);

        return MessageDto.of("다이어리 삭제 완료", HttpStatus.OK);
    }

}
