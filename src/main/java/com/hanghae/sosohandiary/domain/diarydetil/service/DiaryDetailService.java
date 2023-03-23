package com.hanghae.sosohandiary.domain.diarydetil.service;

import com.hanghae.sosohandiary.domain.diary.entity.Diary;
import com.hanghae.sosohandiary.domain.diary.repository.DiaryRepository;
import com.hanghae.sosohandiary.domain.diarydetil.dto.DiaryDetailRequestDto;
import com.hanghae.sosohandiary.domain.diarydetil.dto.DiaryDetailResponseDto;
import com.hanghae.sosohandiary.domain.diarydetil.entity.DiaryDetail;
import com.hanghae.sosohandiary.domain.diarydetil.repository.DiaryDetailRepository;
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

    @Transactional
    public DiaryDetailResponseDto saveDetail(Long id,
                                             DiaryDetailRequestDto diaryDetailRequestDto,
                                             Member member) {

        Diary diary = diaryRepository.findById(id).orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_FOUND_DIARY)
        );

        DiaryDetail diaryDetail = diaryDetailRepository.save(DiaryDetail.of(diaryDetailRequestDto, diary, member));

        return DiaryDetailResponseDto.from(diaryDetail, diary, member);
    }

    public PageCustom<DiaryDetailResponseDto> findListDetail(Long id, Pageable pageable) {
        Diary diary = diaryRepository.findById(id).orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_FOUND_DIARY)
        );
        Page<DiaryDetailResponseDto> diaryDetailResponseDtoPage = diaryDetailRepository.findAllByOrderByModifiedAtDesc(pageable).map(
                (DiaryDetail diaryDetail) -> new DiaryDetailResponseDto(diaryDetail, diary, diaryDetail.getMember())
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

        return DiaryDetailResponseDto.from(diaryDetail, diary, diaryDetail.getMember());
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

        if (!diaryDetail.getMember().getId().equals(member.getId())) {
            throw new ApiException(ErrorHandling.NOT_MATCH_AUTHORIZATION);
        }

        diaryDetail.update(diaryDetailRequestDto);

        return DiaryDetailResponseDto.from(diaryDetail, diary, member);
    }

    @Transactional
    public MessageDto removeDetail(Long diaryId, Long detailId, Member member) {
        diaryRepository.findById(diaryId).orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_FOUND_DIARY)
        );

        DiaryDetail diaryDetail = diaryDetailRepository.findById(detailId).orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_FOUND_DIARY)
        );

        if (!diaryDetail.getMember().getId().equals(member.getId())) {
            throw new ApiException(ErrorHandling.NOT_MATCH_AUTHORIZATION);
        }

        diaryDetailRepository.deleteById(detailId);

        return MessageDto.of("다이어리 삭제 완료", HttpStatus.OK);
    }

}
