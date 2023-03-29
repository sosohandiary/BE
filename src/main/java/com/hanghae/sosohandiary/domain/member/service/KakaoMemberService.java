package com.hanghae.sosohandiary.domain.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghae.sosohandiary.auth.JwtUtil;
import com.hanghae.sosohandiary.domain.member.dto.KakaoMemberInfoDto;
import com.hanghae.sosohandiary.domain.member.entity.Member;
import com.hanghae.sosohandiary.domain.member.entity.MemberRoleEnum;
import com.hanghae.sosohandiary.domain.member.repository.MemberRepository;
import com.hanghae.sosohandiary.security.MemberDetailsServiceImpl;
import com.hanghae.sosohandiary.utils.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoMemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final MemberDetailsServiceImpl memberDetailsService;

    public MessageDto loginKakao(String code, HttpServletResponse response) throws JsonProcessingException {

        String accessToken = getToken(code);

        KakaoMemberInfoDto kakaoMemberInfo = getKakaoMemberInfo(accessToken);

        Member kakaoMember = registerKakaoUserIfNeeded(kakaoMemberInfo);

        forceLoginUser(kakaoMember);
        String createToken = jwtUtil.createToken(kakaoMember.getEmail(), MemberRoleEnum.MEMBER);

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, createToken);

        return new MessageDto("로그인 성공", HttpStatus.ACCEPTED);
    }

    private void forceLoginUser(Member member) {

        UserDetails userDetails = memberDetailsService.loadUserByUsername(member.getEmail());
        Authentication createAuthentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(createAuthentication);
    }

    private String getToken(String code) throws JsonProcessingException {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "b47175c20956cf2b54129db2d3886d94");
        body.add("redirect_uri", "http://localhost:3000/oauth");
        body.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }

    private KakaoMemberInfoDto getKakaoMemberInfo(String accessToken) throws JsonProcessingException {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        System.out.print(jsonNode);
        Long id = jsonNode.get("id").asLong();

        String name = jsonNode.get("properties")
                .get("nickname").asText();
        String email = jsonNode.get("kakao_account")
                .get("email").asText();
        String nickname = jsonNode.get("properties")
                .get("nickname").asText();

        log.info("카카오 사용자 정보: " + id + ", " + email + ", " + name + ", " + nickname);

        return KakaoMemberInfoDto.of(id, email, name, nickname);

    }

    private Member registerKakaoUserIfNeeded(KakaoMemberInfoDto kakaoMemberInfo) {

        Long kakaoId = kakaoMemberInfo.getId();
        Member kakaoMember = memberRepository.findByKakaoId(kakaoId)
                .orElse(null);
        if (kakaoMember == null) {
            String kakaoEmail = kakaoMemberInfo.getEmail();
            Member sameEmailMember = memberRepository.findByEmail(kakaoEmail).orElse(null);
            if (sameEmailMember != null) {
                kakaoMember = sameEmailMember;
                kakaoMember = kakaoMember.kakaoIdUpdate(kakaoId);
            } else {
                String password = UUID.randomUUID().toString();
                String encodedPassword = passwordEncoder.encode(password);

                kakaoMember = Member.of(kakaoMemberInfo, encodedPassword, MemberRoleEnum.MEMBER);
            }
            memberRepository.save(kakaoMember);
        }
        return kakaoMember;
    }


}
