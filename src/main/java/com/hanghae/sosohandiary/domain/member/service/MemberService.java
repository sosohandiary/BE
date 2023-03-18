package com.hanghae.sosohandiary.domain.member.service;


import com.hanghae.sosohandiary.auth.JwtUtil;
import com.hanghae.sosohandiary.domain.member.dto.JoinRequestDto;
import com.hanghae.sosohandiary.domain.member.dto.LoginRequestDto;
import com.hanghae.sosohandiary.domain.member.entity.Member;
import com.hanghae.sosohandiary.domain.member.entity.Gender;
import com.hanghae.sosohandiary.domain.member.entity.MemberRoleEnum;
import com.hanghae.sosohandiary.domain.member.repository.MemberRepository;
import com.hanghae.sosohandiary.exception.ApiException;
import com.hanghae.sosohandiary.utils.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static com.hanghae.sosohandiary.exception.ErrorHandling.*;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Transactional
    public MessageDto join(JoinRequestDto joinRequestDto) {

        String email = joinRequestDto.getEmail();
        String password = passwordEncoder.encode(joinRequestDto.getPassword());
        String name = joinRequestDto.getName();
        String nickname = joinRequestDto.getNickname();
        Gender gender = joinRequestDto.getGender();

        Optional<Member> foundEmail = memberRepository.findByEmail(email);
        if (foundEmail.isPresent()) {
            throw new ApiException(DUPLICATED_EMAIL);
        }

        MemberRoleEnum role = MemberRoleEnum.MEMBER;
        if (joinRequestDto.isAdmin()) {
            if (!joinRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new ApiException(INVALID_ADMIN_TOKEN);
            }
            role = MemberRoleEnum.ADMIN;
        }

        Member member = Member.of(email, password, name, nickname, gender, role);
        memberRepository.save(member);

        return MessageDto.of("회원가입 성공", HttpStatus.ACCEPTED);
    }

    @Transactional
    public MessageDto login(LoginRequestDto loginRequestDto, HttpServletResponse response) {

        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();

        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isEmpty()) {
            throw new ApiException(NOT_FOUND_USER);
        }

        if (!passwordEncoder.matches(password, member.get().getPassword())) {
            throw new ApiException(INVALID_PASSWORD);
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(email, member.get().getRole()));

        return MessageDto.of("로그인 성공", HttpStatus.ACCEPTED);
    }


}
