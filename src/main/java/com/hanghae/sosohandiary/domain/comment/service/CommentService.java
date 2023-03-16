package com.hanghae.sosohandiary.domain.comment.service;

import com.hanghae.sosohandiary.domain.comment.dto.CommentRequestDto;
import com.hanghae.sosohandiary.domain.comment.dto.CommentResponseDto;
import com.hanghae.sosohandiary.domain.comment.entity.Comment;
import com.hanghae.sosohandiary.domain.comment.repository.CommentRepository;
import com.hanghae.sosohandiary.domain.diary.entity.Diary;
import com.hanghae.sosohandiary.domain.diarydetil.dto.DiaryDetailResponseDto;
import com.hanghae.sosohandiary.domain.diarydetil.entity.DiaryDetail;
import com.hanghae.sosohandiary.domain.diarydetil.repository.DiaryDetailRepository;
import com.hanghae.sosohandiary.domain.member.entity.Member;
import com.hanghae.sosohandiary.exception.ApiException;
import com.hanghae.sosohandiary.exception.ErrorHandling;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final DiaryDetailRepository diaryDetailRepository;
    public CommentResponseDto createComment(Long id, CommentRequestDto requestDto, Member member) {
        String comment = requestDto.getComment();
        DiaryDetail diaryDetail = diaryDetailRepository.findById(id).orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_FOUND_DIARY_DETAIL)
        );

        commentRepository.save(Comment.of(diaryDetail, member, comment));

        return CommentResponseDto.from(diaryDetail, member, comment);
    }
}
