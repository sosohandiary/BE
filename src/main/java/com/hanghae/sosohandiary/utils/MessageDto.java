package com.hanghae.sosohandiary.utils;

import com.hanghae.sosohandiary.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MessageDto {

    private String msg;
    private int statusCode;
    private String name;
    private String nickname;

    @Builder
    private MessageDto(String msg, HttpStatus statusCode, String name, String nickname) {
        this.msg = msg;
        this.statusCode = statusCode.value();
        this.name = name;
        this.nickname = nickname;
    }

    @Builder
    public MessageDto(String msg, HttpStatus statusCode) {
        this.msg = msg;
        this.statusCode = statusCode.value();
    }

    public static MessageDto of(String msg, HttpStatus status) {
        return MessageDto.builder()
                .msg(msg)
                .statusCode(status)
                .build();
    }

    public static MessageDto ofLogin(String msg, HttpStatus status, Member member) {
        return MessageDto.builder()
                .msg(msg)
                .statusCode(status)
                .name(member.getName())
                .nickname(member.getNickname())
                .build();
    }

}
