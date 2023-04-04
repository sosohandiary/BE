package com.hanghae.sosohandiary.domain.comment.service;

import com.hanghae.sosohandiary.domain.comment.dto.CommentAlarmResponseDto;
import com.hanghae.sosohandiary.domain.comment.dto.CommentRequestDto;
import com.hanghae.sosohandiary.domain.comment.dto.CommentResponseDto;
import com.hanghae.sosohandiary.domain.comment.entity.Comment;
import com.hanghae.sosohandiary.domain.comment.repository.CommentRepository;
import com.hanghae.sosohandiary.domain.diarydetail.entity.DiaryDetail;
import com.hanghae.sosohandiary.domain.diarydetail.repository.DiaryDetailRepository;
import com.hanghae.sosohandiary.domain.member.entity.Member;
import com.hanghae.sosohandiary.exception.ApiException;
import com.hanghae.sosohandiary.utils.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.hanghae.sosohandiary.exception.ErrorHandling.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final DiaryDetailRepository diaryDetailRepository;

    @Transactional
    public CommentResponseDto createComment(Long id, CommentRequestDto requestDto, Member member) {

        DiaryDetail diaryDetail = diaryDetailRepository.findById(id).orElseThrow(
                () -> new ApiException(NOT_FOUND_DIARY_DETAIL)
        );

        Comment comment = commentRepository.save(Comment.of(diaryDetail, member, requestDto));

        return CommentResponseDto.of(diaryDetail, member, comment);
    }

    public List<CommentResponseDto> getComment(Long id, Member member) {

        DiaryDetail diaryDetail = diaryDetailRepository.findById(id).orElseThrow(
                () -> new ApiException(NOT_FOUND_DIARY_DETAIL)
        );

        List<Comment> commentList = commentRepository.findAllByDiaryDetailIdOrderByCreatedAtDesc(diaryDetail.getId());
        List<CommentResponseDto> commentResponseList = new ArrayList<>();

        for (Comment comment : commentList) {
            commentResponseList.add(CommentResponseDto.of(diaryDetail, member, comment));
        }
        return commentResponseList;
    }

    @Transactional
    public CommentResponseDto updateComment(Long detailId, Long commentId, CommentRequestDto requestDto, Member member) {

        DiaryDetail diaryDetail = diaryDetailRepository.findById(detailId).orElseThrow(
                () -> new ApiException(NOT_FOUND_DIARY_DETAIL)
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ApiException(NOT_FOUND_DIARY_DETAIL_COMMENT)
        );

        if (!comment.getMember().getId().equals(member.getId())) {
            throw new ApiException(NOT_MATCH_AUTHORIZATION);
        }

        comment.update(requestDto.getComment());

        return CommentResponseDto.of(diaryDetail, member, comment);
    }

    @Transactional
    public MessageDto deleteComment(Long detailId, Long commentId, Member member) {

        diaryDetailRepository.findById(detailId).orElseThrow(
                () -> new ApiException(NOT_FOUND_DIARY_DETAIL)
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ApiException(NOT_FOUND_COMMENT)
        );

        if (!comment.getMember().getId().equals(member.getId())) {
            throw new ApiException(NOT_MATCH_AUTHORIZATION);
        }

        commentRepository.deleteById(commentId);
        return MessageDto.of("댓글삭제 완료", HttpStatus.OK);
    }

    public List<CommentAlarmResponseDto> alarmComment(Long detailId, Member member) {

        DiaryDetail diaryDetail = diaryDetailRepository.findById(detailId).orElseThrow(
                () -> new ApiException(NOT_FOUND_DIARY_DETAIL)
        );

        if (!diaryDetail.getNickname().equals(member.getNickname())) {
            throw new ApiException(NOT_MATCH_AUTHORIZATION);
        }

        List<Comment> commentList = commentRepository.findByDiaryDetailIdOrderByModifiedAtDesc(detailId);
        List<CommentAlarmResponseDto> commentAlarmResponseDtoList = new ArrayList<>();
        for (Comment comment : commentList) {
            commentAlarmResponseDtoList.add(CommentAlarmResponseDto.of(diaryDetail, comment));
        }

        return commentAlarmResponseDtoList;
    }

    @Transactional
    public CommentAlarmResponseDto readAlarm(Long detailId, Long commentId, Member member) {

        DiaryDetail diaryDetail = diaryDetailRepository.findById(detailId).orElseThrow(
                () -> new ApiException(NOT_FOUND_DIARY_DETAIL)
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ApiException(NOT_FOUND_COMMENT)
        );

        if (!diaryDetail.getNickname().equals(member.getNickname())) {
            throw new ApiException(NOT_MATCH_AUTHORIZATION);
        }

        comment.updateAlarm(true);
        return CommentAlarmResponseDto.of(diaryDetail, comment);
    }
}
