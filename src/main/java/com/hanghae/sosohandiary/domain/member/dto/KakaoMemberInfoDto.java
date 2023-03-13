package com.hanghae.sosohandiary.domain.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoMemberInfoDto {
    private Long id;
    private String email;
    private String name;

    public KakaoMemberInfoDto(Long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }
}
