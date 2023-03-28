package com.hanghae.sosohandiary.domain.like.service;

import com.hanghae.sosohandiary.domain.diarydetail.entity.DiaryDetail;
import com.hanghae.sosohandiary.domain.diarydetail.repository.DiaryDetailRepository;
import com.hanghae.sosohandiary.domain.like.dto.LikesResponseDto;
import com.hanghae.sosohandiary.domain.like.entity.Likes;
import com.hanghae.sosohandiary.domain.like.repository.LikesRepository;
import com.hanghae.sosohandiary.domain.member.entity.Member;
import com.hanghae.sosohandiary.exception.ApiException;
import com.hanghae.sosohandiary.exception.ErrorHandling;
import com.hanghae.sosohandiary.utils.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {

    private final LikesRepository likesRepository;
    private final DiaryDetailRepository diaryDetailRepository;

    @Transactional
    public MessageDto postLike(Long detailId, Member member) {
        Optional<Likes> diaryDetailLike = likesRepository.findByDiaryDetailIdAndMemberId(detailId, member.getId());

        DiaryDetail diaryDetail = diaryDetailRepository.findById(detailId).orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_FOUND_DIARY_DETAIL)
        );

        if (diaryDetailLike.isPresent()) {
            likesRepository.delete(diaryDetailLike.get());
            return MessageDto.of("좋아요 취소완료", HttpStatus.OK);
        }
        likesRepository.save(Likes.of(member, diaryDetail));
        return MessageDto.of("좋아요 등록완료", HttpStatus.ACCEPTED);
    }

    public List<LikesResponseDto> alarmLike(Long detailId, Member member) {

        DiaryDetail diaryDetail = diaryDetailRepository.findById(detailId).orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_FOUND_DIARY_DETAIL)
        );

        if (!diaryDetail.getNickname().equals(member.getNickname())) {
            throw new ApiException(ErrorHandling.NOT_MATCH_AUTHORIZATION);
        }

        List<Likes> likesList = likesRepository.findAllByDiaryDetailId(detailId);
        List<LikesResponseDto> likesResponseDtoList = new ArrayList<>();
        for (Likes likes : likesList) {
            likesResponseDtoList.add(LikesResponseDto.from(likes, likes.getMember()));
        }

        return likesResponseDtoList;
    }
}
