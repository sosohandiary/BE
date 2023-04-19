package com.hanghae.sosohandiary.domain.comment.service;

import com.hanghae.sosohandiary.domain.comment.dto.CommentAlarmResponseDto;
import com.hanghae.sosohandiary.domain.comment.entity.Comment;
import com.hanghae.sosohandiary.domain.comment.repository.CommentRepository;
import com.hanghae.sosohandiary.domain.diarydetail.entity.DiaryDetail;
import com.hanghae.sosohandiary.domain.diarydetail.repository.DiaryDetailRepository;
import com.hanghae.sosohandiary.domain.member.entity.Member;
import com.hanghae.sosohandiary.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.hanghae.sosohandiary.exception.ErrorHandling.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentAlarmService {

    private final CommentRepository commentRepository;
    private final DiaryDetailRepository diaryDetailRepository;

    public List<CommentAlarmResponseDto> alarmComment(Member member) {

        List<DiaryDetail> diaryDetailList = diaryDetailRepository.findAllByMemberId(member.getId());
        List<CommentAlarmResponseDto> commentAlarmResponseDtoList = new ArrayList<>();
        for (DiaryDetail diaryDetail : diaryDetailList) {
            List<Comment> commentList = commentRepository.findAllByDiaryDetailIdOrderByCreatedAtDesc(diaryDetail.getId());
            for (Comment comment : commentList) {
                if (!comment.getMember().getId().equals(diaryDetail.getDiary().getMember().getId())) {
                    commentAlarmResponseDtoList.add(CommentAlarmResponseDto.of(diaryDetail.getDiary().getId(), diaryDetail, comment));
                }
            }
        }

        return commentAlarmResponseDtoList;
    }

    @Transactional
    public CommentAlarmResponseDto readAlarm(Long commentId, Member member) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ApiException(NOT_FOUND_COMMENT)
        );

        DiaryDetail diaryDetail = diaryDetailRepository.findById(comment.getDiaryDetail().getId()).orElseThrow(
                () -> new ApiException(NOT_FOUND_DIARY_DETAIL)
        );

        if (!comment.getDiaryDetail().getMember().getId().equals(member.getId())) {
            throw new ApiException(NOT_MATCH_AUTHORIZATION);
        }

        comment.updateAlarm(true);
        return CommentAlarmResponseDto.of(diaryDetail.getDiary().getId(), diaryDetail, comment);
    }
}
