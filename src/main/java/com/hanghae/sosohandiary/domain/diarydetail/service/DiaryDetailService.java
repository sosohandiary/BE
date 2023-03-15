package com.hanghae.sosohandiary.domain.diarydetail.service;

import com.hanghae.sosohandiary.domain.diarydetail.dto.DiaryDetailRequestDto;
import com.hanghae.sosohandiary.domain.diarydetail.dto.DiaryDetailResponseDto;
import com.hanghae.sosohandiary.domain.diarydetail.entity.DiaryDetail;
import com.hanghae.sosohandiary.domain.diarydetail.repository.DiaryDetailRepository;
import com.hanghae.sosohandiary.domain.image.entity.Image;
import com.hanghae.sosohandiary.domain.image.repository.ImageRepository;
import com.hanghae.sosohandiary.domain.member.entity.Member;
import com.hanghae.sosohandiary.exception.ApiException;
import com.hanghae.sosohandiary.exception.ErrorHandling;
import com.hanghae.sosohandiary.utils.s3.S3Service;
import lombok.RequiredArgsConstructor;
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

    private final ImageRepository imageRepository;
    private final DiaryDetailRepository diaryRepository;
    private final S3Service s3Service;

    // TODO: 2023-03-13 service 회원 로직 완료 후 member 추가
    @Transactional
    public DiaryDetailResponseDto saveDiary(DiaryDetailRequestDto diaryDetailRequestDto,
                                            List<MultipartFile> multipartFileList,
                                            Member member) throws IOException {

        DiaryDetail diaryDetail = diaryRepository.save(DiaryDetail.of(diaryDetailRequestDto));

        if (multipartFileList != null) {
            s3Service.upload(multipartFileList, "static", diaryDetail);
        }

        return DiaryDetailResponseDto.from(diaryDetail, member);
    }

    public List<DiaryDetailResponseDto> findDiaryList() {
        List<DiaryDetail> diaryDetailList = diaryRepository.findAllByOrderByModifiedAtDesc().orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_FOUND_DIARY)
        );
        List<DiaryDetailResponseDto> diaryDetailResponseDtoList = new ArrayList<>();

        for (DiaryDetail diaryDetail : diaryDetailList) {
            List<String> imgList = imgPathList(diaryDetail);
            diaryDetailResponseDtoList.add(DiaryDetailResponseDto.from(diaryDetail, imgList));
        }

        return diaryDetailResponseDtoList;
    }

    private List<String> imgPathList(DiaryDetail diaryDetail) {
        List<Image> imageList = imageRepository.findAllByDiary(diaryDetail);
        List<String> imgPathList = new ArrayList<>();

        for (Image image : imageList) {
            imgPathList.add(image.getUploadPath());
        }
        return imgPathList;
    }
}
