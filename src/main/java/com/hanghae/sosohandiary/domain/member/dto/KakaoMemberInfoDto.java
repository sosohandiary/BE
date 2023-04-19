package com.hanghae.sosohandiary.domain.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoMemberInfoDto {
    private Long id;
    private String email;
    private String name;
    private String nickname;

    @Builder
    public KakaoMemberInfoDto(Long id, String email, String name, String nickname) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
    }

    public static KakaoMemberInfoDto of(Long id, String email, String name, String nickname) {
        return KakaoMemberInfoDto.builder()
                .id(id)
                .email(email)
                .name(name)
                .nickname(nickname)
                .build();
    }


}
