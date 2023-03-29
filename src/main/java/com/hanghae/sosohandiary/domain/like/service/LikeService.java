package com.hanghae.sosohandiary.domain.like.service;

import com.hanghae.sosohandiary.domain.diarydetail.entity.DiaryDetail;
import com.hanghae.sosohandiary.domain.diarydetail.repository.DiaryDetailRepository;
import com.hanghae.sosohandiary.domain.like.dto.LikesResponseDto;
import com.hanghae.sosohandiary.domain.like.entity.Likes;
import com.hanghae.sosohandiary.domain.like.repository.LikesRepository;
import com.hanghae.sosohandiary.domain.member.entity.Member;
import com.hanghae.sosohandiary.exception.ApiException;
import com.hanghae.sosohandiary.utils.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.hanghae.sosohandiary.exception.ErrorHandling.*;
import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {

    private final LikesRepository likesRepository;
    private final DiaryDetailRepository diaryDetailRepository;

    @Transactional
    public MessageDto postLike(Long detailId, Member member) {

        Likes diaryDetailLike = likesRepository.findByDiaryDetailIdAndMemberId(detailId, member.getId()).orElseThrow(
                () -> new ApiException(INVALID_ACCESS)
        );

        DiaryDetail diaryDetail = diaryDetailRepository.findById(detailId).orElseThrow(
                () -> new ApiException(NOT_FOUND_DIARY_DETAIL)
        );

        if (diaryDetailLike.getMember().getId().equals(member.getId())) {
            likesRepository.delete(diaryDetailLike);
            return MessageDto.of("좋아요 취소 완료", OK);
        }

        likesRepository.save(Likes.of(member, diaryDetail));
        return MessageDto.of("좋아요 등록완료", ACCEPTED);
    }

    public List<LikesResponseDto> alarmLike(Long detailId, Member member) {

        DiaryDetail diaryDetail = diaryDetailRepository.findById(detailId).orElseThrow(
                () -> new ApiException(NOT_FOUND_DIARY_DETAIL)
        );

        if (!diaryDetail.getNickname().equals(member.getNickname())) {
            throw new ApiException(NOT_MATCH_AUTHORIZATION);
        }

        List<Likes> likesList = likesRepository.findAllByDiaryDetailId(detailId);
        List<LikesResponseDto> likesResponseDtoList = new ArrayList<>();
        for (Likes likes : likesList) {
            likesResponseDtoList.add(LikesResponseDto.of(likes, likes.getMember()));
        }

        return likesResponseDtoList;
    }
}
