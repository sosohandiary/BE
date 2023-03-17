package com.hanghae.sosohandiary.domain.diarydetil.service;

import com.hanghae.sosohandiary.domain.diary.entity.Diary;
import com.hanghae.sosohandiary.domain.diary.repository.DiaryRepository;
import com.hanghae.sosohandiary.domain.diarydetil.dto.DiaryDetailRequestDto;
import com.hanghae.sosohandiary.domain.diarydetil.dto.DiaryDetailResponseDto;
import com.hanghae.sosohandiary.domain.diarydetil.entity.DiaryDetail;
import com.hanghae.sosohandiary.domain.diarydetil.repository.DiaryDetailRepository;
import com.hanghae.sosohandiary.domain.image.entity.DiaryDetailImage;
import com.hanghae.sosohandiary.domain.image.repository.DiaryDetailImageRepository;
import com.hanghae.sosohandiary.domain.member.entity.Member;
import com.hanghae.sosohandiary.exception.ApiException;
import com.hanghae.sosohandiary.exception.ErrorHandling;
import com.hanghae.sosohandiary.utils.MessageDto;
import com.hanghae.sosohandiary.utils.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
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

    public List<DiaryDetailResponseDto> findListDetail(Long id) {
        Diary diary = diaryRepository.findById(id).orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_FOUND_DIARY)
        );
        List<DiaryDetail> diaryDetailList = diaryDetailRepository.findAllByOrderByModifiedAtDesc().orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_FOUND_DIARY)
        );
        List<DiaryDetailResponseDto> diaryDetailResponseDtoList = new ArrayList<>();

        for (DiaryDetail diaryDetail : diaryDetailList) {
            List<String> imgList = imgPathList(diaryDetail);
            diaryDetailResponseDtoList.add(DiaryDetailResponseDto.from(diaryDetail, imgList, diary, diaryDetail.getMember()));
        }

        return diaryDetailResponseDtoList;
    }

    private List<String> imgPathList(DiaryDetail diaryDetail) {
        List<DiaryDetailImage> diaryImageList = diaryDetailImageRepository.findAllByDiaryDetail(diaryDetail);
        List<String> imgPathList = new ArrayList<>();

        for (DiaryDetailImage image : diaryImageList) {
            imgPathList.add(image.getUploadPath());
        }
        return imgPathList;
    }

    public DiaryDetailResponseDto findDetail(Long diaryId, Long detailId) {
        Diary diary = diaryRepository.findById(diaryId).orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_FOUND_DIARY)
        );

        DiaryDetail diaryDetail = diaryDetailRepository.findById(detailId).orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_FOUND_DIARY)
        );

        List<String> imgList = imgPathList(diaryDetail);
        return DiaryDetailResponseDto.from(diaryDetail, imgList, diary, diaryDetail.getMember());
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

        List<DiaryDetailImage> diaryDetailImageList = diaryDetailImageRepository.findAllByDiaryDetail(diaryDetail);
        for (DiaryDetailImage diaryImage : diaryDetailImageList) {
            String uploadPath = diaryImage.getUploadPath();
            String filename = uploadPath.substring(50);
            s3Service.deleteFile(filename);
        }
        diaryDetailImageRepository.deleteAllByDiaryDetailId(diaryDetail.getId());

        diaryDetail.update(diaryDetailRequestDto);

        List<String> imagePathList = imgPathList(diaryDetail);
        if (multipartFileList != null) {
            s3Service.uploadDiaryDetail(multipartFileList, diaryDetail, member);
        }

        return DiaryDetailResponseDto.from(diaryDetail, imagePathList, diary, member);
    }

    public MessageDto removeDetail(Long diaryId, Long detailId, Member member) {
        Diary diary = diaryRepository.findById(diaryId).orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_FOUND_DIARY)
        );

        DiaryDetail diaryDetail = diaryDetailRepository.findById(detailId).orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_FOUND_DIARY)
        );

        if (!diaryDetail.getMember().getId().equals(member.getId())) {
            throw new ApiException(ErrorHandling.NOT_MATCH_AUTHORIZATION);
        }

        List<DiaryDetailImage> diaryDetailImageList = diaryDetailImageRepository.findAllByDiaryDetail(diaryDetail);
        for (DiaryDetailImage diaryImage : diaryDetailImageList) {
            String uploadPath = diaryImage.getUploadPath();
            String filename = uploadPath.substring(50);
            s3Service.deleteFile(filename);
        }
        diaryDetailImageRepository.deleteAllByDiaryDetailId(detailId);
        diaryDetailRepository.deleteById(detailId);

        return MessageDto.of("다이어리 삭제 완료", HttpStatus.OK);
    }

}
