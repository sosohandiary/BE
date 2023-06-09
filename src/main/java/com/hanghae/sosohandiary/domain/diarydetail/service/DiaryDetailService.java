package com.hanghae.sosohandiary.domain.diarydetail.service;

import com.hanghae.sosohandiary.domain.comment.repository.CommentRepository;
import com.hanghae.sosohandiary.domain.diary.entity.Diary;
import com.hanghae.sosohandiary.domain.diary.entity.DiaryCondition;
import com.hanghae.sosohandiary.domain.diary.repository.DiaryRepository;
import com.hanghae.sosohandiary.domain.diarydetail.dto.DiaryDetailRequestDto;
import com.hanghae.sosohandiary.domain.diarydetail.dto.DiaryDetailResponseDto;
import com.hanghae.sosohandiary.domain.diarydetail.entity.DiaryDetail;
import com.hanghae.sosohandiary.domain.diarydetail.repository.DiaryDetailRepository;
import com.hanghae.sosohandiary.domain.invite.entity.Invite;
import com.hanghae.sosohandiary.domain.invite.repository.InviteRepository;
import com.hanghae.sosohandiary.domain.like.repository.LikesRepository;
import com.hanghae.sosohandiary.domain.member.entity.Member;
import com.hanghae.sosohandiary.exception.ApiException;
import com.hanghae.sosohandiary.utils.MessageDto;
import com.hanghae.sosohandiary.utils.page.PageCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.hanghae.sosohandiary.exception.ErrorHandling.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiaryDetailService {

    private final DiaryDetailRepository diaryDetailRepository;
    private final DiaryRepository diaryRepository;
    private final LikesRepository likesRepository;
    private final CommentRepository commentRepository;
    private final InviteRepository inviteRepository;

    @Transactional
    public DiaryDetailResponseDto createDetail(Long id,
                                               DiaryDetailRequestDto diaryDetailRequestDto,
                                               Member member) {

        Diary diary = diaryRepository.findById(id).orElseThrow(
                () -> new ApiException(NOT_FOUND_DIARY)
        );

        DiaryDetail diaryDetail = diaryDetailRepository.save(DiaryDetail.of(diaryDetailRequestDto, diary, member));

        isAuthor(member, diary);
        isPrivateMember(member, diary);

        return DiaryDetailResponseDto.of(diaryDetail, diary);
    }

    public List<DiaryDetailResponseDto> findListDetail(Long id) {

        Diary diary = diaryRepository.findById(id).orElseThrow(
                () -> new ApiException(NOT_FOUND_DIARY)
        );
        List<DiaryDetail> diaryDetailList = diaryDetailRepository.findAllByDiaryIdOrderByCreatedAtAsc(id);
        List<DiaryDetailResponseDto> diaryDetailResponseDtoList = new ArrayList<>();
        for (DiaryDetail diaryDetail : diaryDetailList) {
            diaryDetailResponseDtoList.add(DiaryDetailResponseDto.of(diaryDetail, diary,
                    likesRepository.countByDiaryDetailId(diaryDetail.getId()), commentRepository.countCommentsByDiaryDetailId(diaryDetail.getId())));
        }

        return diaryDetailResponseDtoList;
    }

    public DiaryDetailResponseDto findDetail(Long diaryId, Long detailId, Member member) {

        Diary diary = diaryRepository.findById(diaryId).orElseThrow(
                () -> new ApiException(NOT_FOUND_DIARY)
        );

        DiaryDetail diaryDetail = diaryDetailRepository.findByDiaryIdAndId(diaryId, detailId).orElseThrow(
                () -> new ApiException(NOT_FOUND_DIARY_DETAIL)
        );

        List<Invite> inviteList = inviteRepository.findAllByDiaryId(diary.getId());
        List<Long> toMemberId = new ArrayList<>();
        toMemberId.add(diary.getMember().getId());
        for (Invite invite : inviteList) {
            toMemberId.add(invite.getToMember().getId());
        }

        if (diary.getDiaryCondition().equals(DiaryCondition.PRIVATE) &&
                inviteList.stream().noneMatch(invite -> invite.getToMember().getId().equals(member.getId()))) {
            if (!diary.getMember().getId().equals(member.getId())) {
                throw new ApiException(NOT_MATCH_AUTHORIZATION);
            }
        }

        return DiaryDetailResponseDto.of(diaryDetail, diary, diaryDetail.getMember().getId(), toMemberId,
                likesRepository.countByDiaryDetailId(detailId),
                commentRepository.countCommentsByDiaryDetailId(detailId),
                likesRepository.existsByDiaryDetailIdAndMemberId(detailId, member.getId()));
    }

    @Transactional
    public DiaryDetailResponseDto modifyDetail(Long diaryId,
                                               Long detailId,
                                               DiaryDetailRequestDto diaryDetailRequestDto,
                                               Member member) {

        Diary diary = diaryRepository.findById(diaryId).orElseThrow(
                () -> new ApiException(NOT_FOUND_DIARY)
        );

        DiaryDetail diaryDetail = diaryDetailRepository.findById(detailId).orElseThrow(
                () -> new ApiException(NOT_FOUND_DIARY_DETAIL)
        );

        if (!diaryDetail.getMember().getId().equals(member.getId())) {
            throw new ApiException(NOT_MATCH_AUTHORIZATION);
        }

        isAuthor(member, diary);
        isPrivateMember(member, diary);
        diaryDetail.update(diaryDetailRequestDto);

        return DiaryDetailResponseDto.of(diaryDetail, diary);
    }

    @Transactional
    public MessageDto removeDetail(Long diaryId, Long detailId, Member member) {

        diaryRepository.findById(diaryId).orElseThrow(
                () -> new ApiException(NOT_FOUND_DIARY)
        );

        DiaryDetail diaryDetail = diaryDetailRepository.findById(detailId).orElseThrow(
                () -> new ApiException(NOT_FOUND_DIARY_DETAIL)
        );

        if (!diaryDetail.getMember().getId().equals(member.getId())) {
            throw new ApiException(NOT_MATCH_AUTHORIZATION);
        }

        commentRepository.deleteAllByDiaryDetailId(detailId);
        likesRepository.deleteAllByDiaryDetailId(detailId);
        diaryDetailRepository.deleteById(detailId);

        return MessageDto.of("다이어리 삭제 완료", HttpStatus.OK);
    }

    private static void isAuthor(Member member, Diary diary) {
        if (diary.getDiaryCondition().equals(DiaryCondition.PUBLIC) && !diary.getMember().getId().equals(member.getId())) {
            throw new ApiException(NOT_MATCH_AUTHORIZATION);
        }
    }

    private void isPrivateMember(Member member, Diary diary) {
        List<Invite> inviteList = inviteRepository.findAllByDiaryId(diary.getId());

        if (inviteList.stream().noneMatch(invite -> invite.getToMember().getId().equals(member.getId()))) {
            if (!diary.getMember().getId().equals(member.getId())) {
                throw new ApiException(NOT_MATCH_AUTHORIZATION);
            }
        }
    }
}
