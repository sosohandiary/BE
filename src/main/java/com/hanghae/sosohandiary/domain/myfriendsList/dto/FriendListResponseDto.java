package com.hanghae.sosohandiary.domain.myfriendsList.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FriendListResponseDto {
    private Long id;
    private String email;

    @Builder
    private FriendListResponseDto(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public static FriendListResponseDto from(Long id, String email) {
        return FriendListResponseDto.builder()
                .id(id)
                .email(email)
                .build();
    }


}
