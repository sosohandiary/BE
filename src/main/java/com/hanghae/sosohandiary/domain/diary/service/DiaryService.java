package com.hanghae.sosohandiary.domain.diary.service;

import com.hanghae.sosohandiary.domain.diary.dto.DiaryRequestDto;
import com.hanghae.sosohandiary.domain.diary.dto.DiaryResponseDto;
import com.hanghae.sosohandiary.domain.diary.entity.Diary;
import com.hanghae.sosohandiary.domain.diary.repository.DiaryRepository;
import com.hanghae.sosohandiary.domain.diarydetil.entity.DiaryDetail;
import com.hanghae.sosohandiary.domain.diarydetil.repository.DiaryDetailRepository;
import com.hanghae.sosohandiary.domain.image.entity.DiaryDetailImage;
import com.hanghae.sosohandiary.domain.image.entity.DiaryImage;
import com.hanghae.sosohandiary.domain.image.repository.DiaryDetailImageRepository;
import com.hanghae.sosohandiary.domain.image.repository.DiaryImageRepository;
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
public class DiaryService {

    private final DiaryDetailImageRepository diaryDetailImageRepository;
    private final DiaryImageRepository diaryImageRepository;
    private final DiaryDetailRepository diaryDetailRepository;
    private final DiaryRepository diaryRepository;
    private final S3Service s3Service;

    @Transactional
    public DiaryResponseDto saveDiary(DiaryRequestDto diaryRequestDto,
                                      List<MultipartFile> multipartFileList,
                                      Member member) throws IOException {

        Diary diary = diaryRepository.save(Diary.of(diaryRequestDto, member));

        if (multipartFileList != null) {
            s3Service.uploadDiary(multipartFileList, diary, member);
        }

        return DiaryResponseDto.from(diary, member);
    }

    public List<DiaryResponseDto> findDiaryList() {
        List<Diary> diaryList = diaryRepository.findAllByOrderByModifiedAtDesc().orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_FOUND_DIARY)
        );
        List<DiaryResponseDto> diaryResponseDtoList = new ArrayList<>();

        for (Diary diary : diaryList) {
            List<String> imgList = imgPathList(diary);
            diaryResponseDtoList.add(DiaryResponseDto.from(diary, imgList, diary.getMember()));
        }

        return diaryResponseDtoList;
    }

    private List<String> imgPathList(Diary diary) {
        List<DiaryImage> diaryImageList = diaryImageRepository.findAllByDiary(diary);
        List<String> imgPathList = new ArrayList<>();

        for (DiaryImage image : diaryImageList) {
            imgPathList.add(image.getUploadPath());
        }
        return imgPathList;
    }

    @Transactional
    public DiaryResponseDto modifyDiary(Long id, DiaryRequestDto diaryRequestDto, List<MultipartFile> multipartFileList, Member member) throws IOException {
        Diary diary = diaryRepository.findById(id).orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_FOUND_DIARY)
        );

        if (!diary.getMember().getId().equals(member.getId())) {
            throw new ApiException(ErrorHandling.NOT_MATCH_AUTHORIZATION);
        }

        List<DiaryImage> diaryImageList = diaryImageRepository.findAllByDiary(diary);
        for (DiaryImage diaryImage : diaryImageList) {
            String uploadPath = diaryImage.getUploadPath();
            String filename = uploadPath.substring(50);
            s3Service.deleteFile(filename);
        }
        diaryImageRepository.deleteAllByDiaryId(diary.getId());

        diary.update(diaryRequestDto);

        List<String> imagePathList = imgPathList(diary);
        if (multipartFileList != null) {
            s3Service.uploadDiary(multipartFileList, diary, member);
        }

        return DiaryResponseDto.from(diary, imagePathList, member);
    }

    @Transactional
    public MessageDto removeDiary(Long id, Member member) {

        Diary diary = diaryRepository.findById(id).orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_FOUND_DIARY)
        );

        if (!diary.getMember().getId().equals(member.getId())) {
            throw new ApiException(ErrorHandling.NOT_MATCH_AUTHORIZATION);
        }

        List<DiaryImage> diaryImageList = diaryImageRepository.findAllByDiary(diary);
        for (DiaryImage diaryImage : diaryImageList) {
            String uploadPath = diaryImage.getUploadPath();
            String filename = uploadPath.substring(50);
            s3Service.deleteFile(filename);
        }
        List<DiaryDetail> diaryDetailList = diaryDetailRepository.findAllByDiaryId(id).orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_FOUND_DIARY)
        );
        for (DiaryDetail diaryDetail : diaryDetailList) {
            List<DiaryDetailImage> diaryDetailImageList = diaryDetailImageRepository.findAllByDiaryDetail(diaryDetail);
            for (DiaryDetailImage diaryImage : diaryDetailImageList) {
                String uploadPath = diaryImage.getUploadPath();
                String filename = uploadPath.substring(50);
                s3Service.deleteFile(filename);
            }
            diaryDetailImageRepository.deleteAllByDiaryDetailId(diaryDetail.getId());
        }

        diaryImageRepository.deleteAllByDiaryId(id);
        diaryDetailRepository.deleteAllByDiaryId(id);
        diaryRepository.deleteById(id);

        return MessageDto.of("다이어리 삭제 완료", HttpStatus.OK);
    }
}
