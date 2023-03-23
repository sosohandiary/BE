package com.hanghae.sosohandiary.domain.decoration.dto;

import com.hanghae.sosohandiary.domain.friend.dto.FriendResponseDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DecorationResponseDto {

    private Long id;

    private String imageURL;

    @Builder
    private DecorationResponseDto(Long id, String imageURL) {
        this.id = id;
        this.imageURL = imageURL;
    }

    public static DecorationResponseDto from(Long id, String imageURL) {
        return DecorationResponseDto.builder()
                .id(id)
                .imageURL(imageURL)
                .build();
    }
}
