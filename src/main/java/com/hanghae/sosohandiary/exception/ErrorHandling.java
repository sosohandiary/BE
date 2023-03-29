package com.hanghae.sosohandiary.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorHandling {

    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "해당하는 유저가 존재하지 않습니다"),
    NOT_FOUND_DIARY(HttpStatus.NOT_FOUND, "해당 하는 diary를 찾을 수 없습니다"),
    NOT_FOUND_INVITE(HttpStatus.BAD_REQUEST, "해당하는 초대를 보낸 적이 없습니다"),
    DUPLICATED_EMAIL(HttpStatus.CONFLICT, "중복된 이메일 입니다"),
    DUPLICATED_NICKNAME(HttpStatus.CONFLICT, "중복된 닉네임 입니다"),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다"),
    INVALID_ADMIN_TOKEN(HttpStatus.FORBIDDEN, "유효하지 않은 ADMIN 토큰입니다"),
    NOT_FOUND_DIARY_DETAIL(HttpStatus.NOT_FOUND, "해당 하는 diary 속지를 찾을 수 없습니다"),
    NOT_FOUND_DIARY_DETAIL_COMMENT(HttpStatus.NOT_FOUND, "해당 하는 diary 속지의 댓글을 찾을 수 없습니다"),
    NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다"),
    NOT_MATCH_AUTHORIZATION(HttpStatus.FORBIDDEN, "작성자만 삭제/수정할 수 있습니다"),
    NOT_FRIEND_REQUEST(HttpStatus.NOT_FOUND, "해당하는 친구 요청이 없습니다"),
    INVALID_ACCESS(HttpStatus.BAD_REQUEST, "유효하지 않은 접근입니다"),
    DUPLICATED_REQUEST(HttpStatus.CONFLICT, "중복된 요청입니다"),
    OVER_THE_LIMIT(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED, "초대할 인원이 초과되었습니다");

    private final HttpStatus httpStatus;
    private final String msg;

}
