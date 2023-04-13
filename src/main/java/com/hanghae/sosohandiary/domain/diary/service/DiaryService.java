package com.hanghae.sosohandiary.domain.diary.service;

import com.hanghae.sosohandiary.domain.comment.repository.CommentRepository;
import com.hanghae.sosohandiary.domain.diary.dto.DiaryRequestDto;
import com.hanghae.sosohandiary.domain.diary.dto.DiaryResponseDto;
import com.hanghae.sosohandiary.domain.diary.entity.Diary;
import com.hanghae.sosohandiary.domain.diary.entity.DiaryCondition;
import com.hanghae.sosohandiary.domain.diary.repository.DiaryRepository;
import com.hanghae.sosohandiary.domain.diarydetail.entity.DiaryDetail;
import com.hanghae.sosohandiary.domain.diarydetail.repository.DiaryDetailRepository;
import com.hanghae.sosohandiary.domain.invite.entity.Invite;
import com.hanghae.sosohandiary.domain.invite.repository.InviteRepository;
import com.hanghae.sosohandiary.domain.like.repository.LikesRepository;
import com.hanghae.sosohandiary.domain.member.entity.Member;
import com.hanghae.sosohandiary.exception.ApiException;
import com.hanghae.sosohandiary.utils.MessageDto;
import com.hanghae.sosohandiary.utils.page.PageCustom;
import com.hanghae.sosohandiary.utils.s3.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.hanghae.sosohandiary.domain.diary.entity.DiaryCondition.PRIVATE;
import static com.hanghae.sosohandiary.domain.diary.entity.DiaryCondition.PUBLIC;
import static com.hanghae.sosohandiary.exception.ErrorHandling.NOT_FOUND_DIARY;
import static com.hanghae.sosohandiary.exception.ErrorHandling.NOT_MATCH_AUTHORIZATION;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiaryService {

    private final CommentRepository commentRepository;
    private final LikesRepository likesRepository;
    private final DiaryDetailRepository diaryDetailRepository;
    private final DiaryRepository diaryRepository;
    private final InviteRepository inviteRepository;
    private final S3Service s3Service;

    @Transactional
    public DiaryResponseDto saveDiary(DiaryRequestDto diaryRequestDto,
                                      List<MultipartFile> multipartFileList,
                                      Member member) throws IOException {

        for (MultipartFile multipartFile : multipartFileList) {
            if (multipartFile.getOriginalFilename().equals("")) {
                Diary diary = diaryRepository.save(Diary.of(diaryRequestDto, null, member));
                return DiaryResponseDto.of(diary, member);
            }
        }

        if (multipartFileList != null) {
            s3Service.uploadDiary(multipartFileList, diaryRequestDto, member);
        }

        String uploadImageUrl = s3Service.getUploadImageUrl();

        DiaryCondition condition = PUBLIC;

        if (diaryRequestDto.getDiaryCondition().equals(PRIVATE)) {
            condition = PRIVATE;
        }

        Diary diary = diaryRepository.save(Diary.of(diaryRequestDto, uploadImageUrl, member));

        return DiaryResponseDto.of(diary, member);
    }

    public List<DiaryResponseDto> findDiaryList(Pageable pageable, Member member) {

        Page<Diary> diaryPage = diaryRepository.findAllByOrderByModifiedAtDesc(pageable);
        List<DiaryResponseDto> diaryResponseDtoList = new ArrayList<>();
        for (Diary diary : diaryPage) {
            if (diary.getDiaryCondition().equals(PUBLIC)) {
                diaryResponseDtoList.add(DiaryResponseDto.of(diary, diary.getMember()));
            } else if (diary.getMember().getId().equals(member.getId())) {
                diaryResponseDtoList.add(DiaryResponseDto.of(diary, diary.getMember()));
            }
        }

        return diaryResponseDtoList;
    }

    public PageCustom<DiaryResponseDto> findPublicDiaryList(Pageable pageable) {

        Page<DiaryResponseDto> diaryResponseDtoPagePublic = diaryRepository
                .findAllByDiaryConditionOrderByModifiedAtDesc(pageable, PUBLIC)
                .map((Diary diary) -> new DiaryResponseDto(diary, diary.getMember()));

        return new PageCustom<>(diaryResponseDtoPagePublic.getContent(),
                diaryResponseDtoPagePublic.getPageable(),
                diaryResponseDtoPagePublic.getTotalElements());
    }

    public List<DiaryResponseDto> findPrivateDiaryList(Pageable pageable, Member member) {

        List<Diary> diaryList = diaryRepository
                .findAllByMemberIdAndDiaryConditionOrderByModifiedAtDesc(pageable, member.getId(), PRIVATE);
        List<DiaryResponseDto> diaryResponseDtoList = new ArrayList<>();

        for (Diary diary : diaryList) {
            diaryResponseDtoList.add(DiaryResponseDto.of(diary, diary.getMember()));
        }
        return diaryResponseDtoList;
    }

    public PageCustom<DiaryResponseDto> findInvitedDiaryList(Pageable pageable, Member member) {

        List<Invite> inviteList = inviteRepository.findAllByToMemberId(member.getId());

        Page<DiaryResponseDto> diaryResponseDtoPage = null;

        for (Invite invite : inviteList) {
            diaryResponseDtoPage = diaryRepository
                    .findAllByIdOrderByModifiedAtDesc(pageable, invite.getDiary().getId())
                    .map((Diary diary) -> new DiaryResponseDto(diary, diary.getMember()));
        }

        try {
            return new PageCustom<>(diaryResponseDtoPage.getContent(),
                    diaryResponseDtoPage.getPageable(),
                    diaryResponseDtoPage.getTotalElements());
        } catch (NullPointerException e) {
            e.getMessage();
        }

        return null;
    }

    @Transactional
    public DiaryResponseDto modifyDiary(Long id, DiaryRequestDto diaryRequestDto,
                                        List<MultipartFile> multipartFileList,
                                        Member member) throws IOException {

        Diary diary = getDiary(id);

        if (!diary.getMember().getId().equals(member.getId())) {
            throw new ApiException(NOT_MATCH_AUTHORIZATION);
        }

        if (diary.getImg() != null) {
            String uploadPath = diary.getImg();
            String filename = uploadPath.substring(50);
            s3Service.deleteFile(filename);
        }

        for (MultipartFile multipartFile : multipartFileList) {

            if (multipartFile.getOriginalFilename().equals("")) {
                String uploadImageUrl = diary.getImg();
                diary.update(diaryRequestDto, uploadImageUrl);
                return DiaryResponseDto.of(diary, member);
            }
        }

        s3Service.uploadDiary(multipartFileList, diaryRequestDto, member);

        String uploadImageUrl = s3Service.getUploadImageUrl();

        diary.update(diaryRequestDto, uploadImageUrl);

        return DiaryResponseDto.of(diary, member);
    }

    @Transactional
    public MessageDto removeDiary(Long id, Member member) {

        Diary diary = getDiary(id);

        if (!diary.getMember().getId().equals(member.getId())) {
            throw new ApiException(NOT_MATCH_AUTHORIZATION);
        }

        if (diary.getImg() != null) {
            String uploadPath = diary.getImg();
            String filename = uploadPath.substring(50);
            s3Service.deleteFile(filename);
        }
        List<DiaryDetail> diaryDetailList = diaryDetailRepository.findAllByDiaryId(id);
        for (DiaryDetail diaryDetail : diaryDetailList) {
            likesRepository.deleteAllByDiaryDetailId(diaryDetail.getId());
            commentRepository.deleteAllByDiaryDetailId(diaryDetail.getId());
        }
        diaryDetailRepository.deleteAllByDiaryId(id);
        diaryRepository.deleteById(id);

        return MessageDto.of("다이어리 속지 삭제 완료", HttpStatus.OK);
    }

    private Diary getDiary(Long id) {

        return diaryRepository.findById(id).orElseThrow(
                () -> new ApiException(NOT_FOUND_DIARY)
        );
    }

}
