package com.hanghae.sosohandiary.domain.myfriendsList.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MyFriendResponseDto {
    private Long id;
    private String email;


    @Builder
    private MyFriendResponseDto (Long id, String email){
        this.id=id;
        this.email=email;
    }

    public static MyFriendResponseDto from(Long id, String email){
        return MyFriendResponseDto.builder()
                .id(id)
                .email(email)
                .build();
    }
}
