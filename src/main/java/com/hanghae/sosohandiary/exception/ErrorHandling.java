package com.hanghae.sosohandiary.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorHandling {

    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "토큰이 유효하지 않습니다"),
    NOT_FOUND_TOKEN(HttpStatus.NOT_FOUND, "토큰을 찾을 수 없습니다"),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "해당하는 유저가 존재하지 않습니다"),
    NOT_FOUND_EMAIL(HttpStatus.NOT_FOUND, "해당하는 이메일이 존재하지 않습니다"),
    NOT_FOUND_DIARY(HttpStatus.BAD_REQUEST, "해당 하는 diary를 찾을 수 없습니다"),
    NOT_FOUND_INVITE(HttpStatus.NOT_FOUND, "해당하는 초대를 보낸 적이 없습니다"),
    DUPLICATED_EMAIL(HttpStatus.CONFLICT, "중복된 이메일 입니다"),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다"),
    INVALID_ADMIN_TOKEN(HttpStatus.FORBIDDEN, "유효하지 않은 ADMIN 토큰입니다."),
    JWT_EXPIRED_TOKEN(HttpStatus.BAD_REQUEST, "다시 로그인 해주세요"),
    NOT_FOUND_DIARY_DETAIL(HttpStatus.BAD_REQUEST, "해당 하는 diary 속지를 찾을 수 없습니다"),
    NOT_FOUND_DIARY_DETAIL_COMMENT(HttpStatus.BAD_REQUEST, "해당 하는 diary 속지의 댓글을 찾을 수 없습니다"),
    NOT_FOUND_COMMENT(HttpStatus.BAD_REQUEST, "댓글을 찾을 수 없습니다"),
    NOT_MATCH_AUTHORIZATION(HttpStatus.BAD_REQUEST, "작성자만 삭제/수정할 수 있습니다"),
    NOT_REQUEST(HttpStatus.BAD_REQUEST, "친구 요청이 없습니다");

    private final HttpStatus httpStatus;
    private final String msg;

}
