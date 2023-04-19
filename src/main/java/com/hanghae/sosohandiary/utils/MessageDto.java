package com.hanghae.sosohandiary.utils;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MessageDto {

    private String msg;
    private int statusCode;


    @Builder
    private MessageDto(String msg, HttpStatus statusCode) {
        this.msg = msg;
        this.statusCode = statusCode.value();
    }

    public static MessageDto of(String msg, HttpStatus status) {
        return MessageDto.builder()
                .msg(msg)
                .statusCode(status)
                .build();
    }


}
