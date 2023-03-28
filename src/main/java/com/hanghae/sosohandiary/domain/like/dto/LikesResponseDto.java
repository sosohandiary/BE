package com.hanghae.sosohandiary.domain.like.dto;

import com.hanghae.sosohandiary.domain.like.entity.Likes;
import com.hanghae.sosohandiary.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LikesResponseDto {

    private Long id;
    private String nickname;

    @Builder
    private LikesResponseDto(Likes likes, Member member) {
        id = likes.getId();
        nickname = member.getNickname();
    }

    public static LikesResponseDto from(Likes likes, Member member) {
        return LikesResponseDto.builder()
                .likes(likes)
                .member(member)
                .build();
    }
}
