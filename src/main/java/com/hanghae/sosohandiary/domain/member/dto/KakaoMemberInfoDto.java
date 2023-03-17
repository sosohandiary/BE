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
    private String birthday;
    private String gender;

    @Builder
    public KakaoMemberInfoDto(Long id, String email, String name, String nickname, String birthday, String gender) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.birthday = birthday;
        this.gender = gender;
    }

    public static KakaoMemberInfoDto of(Long id, String email, String name, String nickname, String birthday, String gender) {
        return KakaoMemberInfoDto.builder()
                .id(id)
                .email(email)
                .name(name)
                .nickname(nickname)
                .birthday(birthday)
                .gender(gender)
                .build();
    }


}
