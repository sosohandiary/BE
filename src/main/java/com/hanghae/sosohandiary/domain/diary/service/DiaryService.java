package com.hanghae.sosohandiary.domain.diary.service;

import com.hanghae.sosohandiary.domain.diary.dto.DiaryRequestDto;
import com.hanghae.sosohandiary.domain.diary.dto.DiaryResponseDto;
import com.hanghae.sosohandiary.domain.diary.entity.Diary;
import com.hanghae.sosohandiary.domain.diary.repository.DiaryRepository;
import com.hanghae.sosohandiary.domain.image.entity.DiaryImage;
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

    private final DiaryImageRepository diaryImageRepository;
    private final DiaryRepository diaryRepository;
    private final S3Service s3Service;

    // TODO: 2023-03-13 service 회원 로직 완료 후 member 추가
    @Transactional
    public DiaryResponseDto saveDiary(DiaryRequestDto diaryRequestDto,
                                      List<MultipartFile> multipartFileList,
                                      Member member) throws IOException {

        Diary diary = diaryRepository.save(Diary.of(diaryRequestDto, member));

        if (multipartFileList != null) {
            s3Service.uploadDiary(multipartFileList, "static", diary, member);
        }

        return DiaryResponseDto.from(diary, member);
    }

    public List<DiaryResponseDto> findDiaryList() {
        List<Diary> diaryDetailList = diaryRepository.findAllByOrderByModifiedAtDesc().orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_FOUND_DIARY)
        );
        List<DiaryResponseDto> diaryResponseDtoList = new ArrayList<>();

        for (Diary diary : diaryDetailList) {
            List<String> imgList = imgPathList(diary);
            diaryResponseDtoList.add(DiaryResponseDto.from(diary, imgList, diary.getMember()));
        }

        return diaryResponseDtoList;
    }

    private List<String> imgPathList(Diary diaryDetail) {
        List<DiaryImage> diaryImageList = diaryImageRepository.findAllByDiary(diaryDetail);
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

        diary.update(diaryRequestDto);

        if (multipartFileList != null) {
            s3Service.uploadDiary(multipartFileList, "static", diary, member);
        }

        return DiaryResponseDto.from(diary, member);
    }

    @Transactional
    public MessageDto removeDiary(Long id, Member member) {

        Diary diary = diaryRepository.findById(id).orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_FOUND_DIARY)
        );

        if (!diary.getMember().getId().equals(member.getId())) {
            throw new ApiException(ErrorHandling.NOT_MATCH_AUTHORIZATION);
        }

        List<DiaryImage> imagePathList = diaryImageRepository.findAllByDiary(diary);
        for (DiaryImage diaryImage : imagePathList) {
            String uploadPath = diaryImage.getUploadPath();
            String filename = uploadPath.substring(57);
            s3Service.deleteFile(filename);
        }

        diaryImageRepository.deleteAllByDiaryId(diary.getId());
        diaryRepository.deleteById(diary.getId());

        return MessageDto.of("다이어리 삭제 완료", HttpStatus.OK);
    }
}
