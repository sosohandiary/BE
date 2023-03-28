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
import com.hanghae.sosohandiary.exception.ErrorHandling;
import com.hanghae.sosohandiary.utils.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final DiaryDetailRepository diaryDetailRepository;

    public CommentResponseDto createComment(Long id, CommentRequestDto requestDto, Member member) {

        DiaryDetail diaryDetail = diaryDetailRepository.findById(id).orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_FOUND_DIARY_DETAIL)
        );

        Comment comment = commentRepository.save(Comment.of(diaryDetail, member, requestDto));

        return CommentResponseDto.of(diaryDetail, member, comment);
    }

    public List<CommentResponseDto> getComment(Long id, Member member) {

        DiaryDetail diaryDetail = diaryDetailRepository.findById(id).orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_FOUND_DIARY_DETAIL)
        );

        List<Comment> commentList = commentRepository.findAllByDiaryDetailIdOrderByCreatedAtDesc(diaryDetail.getId());
        List<CommentResponseDto> commentResponseList = new ArrayList<>();

        for (Comment comment : commentList) {
            commentResponseList.add(CommentResponseDto.of(diaryDetail, member, comment));
        }
        return commentResponseList;
    }

    @Transactional
    public CommentResponseDto updateComment(Long detailId, Long id, CommentRequestDto requestDto, Member member) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_FOUND_DIARY_DETAIL_COMMENT)
        );
        DiaryDetail diaryDetail = diaryDetailRepository.findById(detailId).orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_FOUND_DIARY_DETAIL)
        );

        if (!comment.getMember().getId().equals(member.getId())) {
            throw new ApiException(ErrorHandling.NOT_MATCH_AUTHORIZATION);
        }

        comment.update(requestDto.getComment());

        return CommentResponseDto.of(diaryDetail, member, comment);
    }

    public MessageDto deleteComment(Long detailId, Long id, Member member) {
        Optional<Comment> comment = commentRepository.findById(id);
        diaryDetailRepository.findById(detailId).orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_FOUND_DIARY_DETAIL)
        );

        if (comment.isPresent()) {
            if (!comment.get().getMember().getId().equals(member.getId())) {
                return new MessageDto("작성자만 삭제 가능합니다", HttpStatus.BAD_REQUEST);
            }
        }

        commentRepository.findById(id).orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_FOUND_DIARY_DETAIL_COMMENT)
        );

        commentRepository.deleteById(id);
        return new MessageDto("댓글삭제 완료", HttpStatus.OK);
    }


    public List<CommentAlarmResponseDto> alarmComment(Long detailId, Member member) {
        DiaryDetail diaryDetail = diaryDetailRepository.findById(detailId).orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_FOUND_DIARY_DETAIL)
        );

        if (!diaryDetail.getNickname().equals(member.getNickname())) {
            throw new ApiException(ErrorHandling.NOT_MATCH_AUTHORIZATION);
        }

        List<Comment> commentList = commentRepository.findByDiaryDetailIdOrderByModifiedAtDesc(detailId);
        List<CommentAlarmResponseDto> commentAlarmResponseDtoList = new ArrayList<>();
        for (Comment comment : commentList) {
            commentAlarmResponseDtoList.add(CommentAlarmResponseDto.of(diaryDetail, comment));
        }

        return commentAlarmResponseDtoList;
    }
}
