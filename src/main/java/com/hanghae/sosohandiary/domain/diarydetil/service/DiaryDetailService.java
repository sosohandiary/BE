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
import com.hanghae.sosohandiary.utils.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiaryDetailService {

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

        for (MultipartFile multipartFile : multipartFileList) {
            if (multipartFile.getOriginalFilename().equals("")) {
                DiaryDetail diaryDetail = diaryDetailRepository.save(DiaryDetail.of(diaryDetailRequestDto, null, diary, member));
                return DiaryDetailResponseDto.from(diaryDetail, diary, member);
            }
        }

        if (multipartFileList != null) {
            s3Service.uploadDiaryDetail(multipartFileList, diaryDetailRequestDto, member);
        }

        String uploadImageUrl = s3Service.getUploadImageUrl();

        DiaryDetail diaryDetail = diaryDetailRepository.save(DiaryDetail.of(diaryDetailRequestDto, uploadImageUrl, diary, member));

        return DiaryDetailResponseDto.from(diaryDetail, diary, member);
    }

    public Page<DiaryDetailResponseDto> findListDetail(Long id, Pageable pageable) {
        Diary diary = diaryRepository.findById(id).orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_FOUND_DIARY)
        );
        Page<DiaryDetail> diaryDetailList = diaryDetailRepository.findAllByOrderByModifiedAtDesc(pageable);

        return diaryDetailList.map((DiaryDetail diaryDetail) -> new DiaryDetailResponseDto(diaryDetail, diary, diaryDetail.getMember()));
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
                                               List<MultipartFile> multipartFileList,
                                               Member member) throws IOException {
        Diary diary = diaryRepository.findById(diaryId).orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_FOUND_DIARY)
        );

        DiaryDetail diaryDetail = diaryDetailRepository.findById(detailId).orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_FOUND_DIARY)
        );

        if (!diaryDetail.getMember().getId().equals(member.getId())) {
            throw new ApiException(ErrorHandling.NOT_MATCH_AUTHORIZATION);
        }

        if (diaryDetail.getImg() != null) {
            String uploadPath = diaryDetail.getImg();
            String filename = uploadPath.substring(50);
            s3Service.deleteFile(filename);
        }

        for (MultipartFile multipartFile : multipartFileList) {
            if (multipartFile.getOriginalFilename().equals("")) {
                String uploadImageUrl = diaryDetail.getImg();
                diaryDetail.update(diaryDetailRequestDto, uploadImageUrl);
                return DiaryDetailResponseDto.from(diaryDetail, diary, member);
            }
        }

        if (multipartFileList != null) {
            s3Service.uploadDiaryDetail(multipartFileList, diaryDetailRequestDto, member);
        }

        String uploadImageUrl = s3Service.getUploadImageUrl();

        diaryDetail.update(diaryDetailRequestDto, uploadImageUrl);

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

        List<DiaryDetail> diaryDetailList = diaryDetailRepository.findAllById(diaryDetail.getId()).orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_FOUND_DIARY_DETAIL)
        );
        for (DiaryDetail diaryImage : diaryDetailList) {
            if (diaryImage.getImg() != null) {
                String uploadPath = diaryImage.getImg();
                String filename = uploadPath.substring(50);
                s3Service.deleteFile(filename);
            }
        }

        diaryDetailRepository.deleteById(detailId);

        return MessageDto.of("다이어리 삭제 완료", HttpStatus.OK);
    }

}
