package com.hanghae.sosohandiary.domain.diarydetil.service;

import com.hanghae.sosohandiary.domain.diary.dto.DiaryRequestDto;
import com.hanghae.sosohandiary.domain.diary.entity.Diary;
import com.hanghae.sosohandiary.domain.diary.repository.DiaryRepository;
import com.hanghae.sosohandiary.domain.diarydetil.dto.DiaryDetailRequestDto;
import com.hanghae.sosohandiary.domain.diarydetil.dto.DiaryDetailResponseDto;
import com.hanghae.sosohandiary.domain.diarydetil.entity.DiaryDetail;
import com.hanghae.sosohandiary.domain.diarydetil.repository.DiaryDetailRepository;
import com.hanghae.sosohandiary.domain.image.repository.DiaryDetailImageRepository;
import com.hanghae.sosohandiary.domain.member.entity.Member;
import com.hanghae.sosohandiary.exception.ApiException;
import com.hanghae.sosohandiary.exception.ErrorHandling;
import com.hanghae.sosohandiary.utils.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiaryDetailService {

    private final DiaryDetailImageRepository diaryDetailImageRepository;
    private final DiaryDetailRepository diaryDetailRepository;
    private final DiaryRepository diaryRepository;
    private final S3Service s3Service;

    @Transactional
    public DiaryDetailResponseDto saveDetail(Long id,
                                             DiaryDetailRequestDto diaryDetailRequestDto,
                                             List<MultipartFile> multipartFileList,
                                             Member member) throws IOException {

        Diary diary = diaryRepository.findById(id).orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_FOUND_DIARY)
        );

        DiaryDetail diaryDetail = diaryDetailRepository.save(DiaryDetail.of(diaryDetailRequestDto, diary, member));

        if (multipartFileList != null) {
            s3Service.uploadDiaryDetail(multipartFileList, diaryDetail, member);
        }

        return DiaryDetailResponseDto.from(diaryDetail, diary, member);
    }
}
