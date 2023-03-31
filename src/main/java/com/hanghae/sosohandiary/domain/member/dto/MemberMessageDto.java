package com.hanghae.sosohandiary.domain.member.dto;

import com.hanghae.sosohandiary.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MemberMessageDto {

    private String msg;
    private int statusCode;
    private Long memberId;
    private String name;
    private String nickname;


    @Builder
    private MemberMessageDto(String msg, HttpStatus statusCode, Member member) {
        this.msg = msg;
        this.statusCode = statusCode.value();
        this.memberId = member.getId();
        this.name = member.getName();
        this.nickname = member.getNickname();
    }

    public static MemberMessageDto of(String msg, HttpStatus statusCode, Member member) {
        return MemberMessageDto.builder()
                .msg(msg)
                .statusCode(statusCode)
                .member(member)
                .build();
    }


}
