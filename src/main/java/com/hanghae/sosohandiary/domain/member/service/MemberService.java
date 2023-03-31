package com.hanghae.sosohandiary.domain.member.service;


import com.hanghae.sosohandiary.auth.JwtUtil;
import com.hanghae.sosohandiary.domain.friend.entity.Friend;
import com.hanghae.sosohandiary.domain.friend.entity.StatusFriend;
import com.hanghae.sosohandiary.domain.friend.repository.FriendRepository;
import com.hanghae.sosohandiary.domain.member.dto.JoinRequestDto;
import com.hanghae.sosohandiary.domain.member.dto.LoginRequestDto;
import com.hanghae.sosohandiary.domain.member.dto.MemberMessageDto;
import com.hanghae.sosohandiary.domain.member.dto.MemberResponseDto;
import com.hanghae.sosohandiary.domain.member.entity.Member;
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
import java.util.ArrayList;
import java.util.List;

import static com.hanghae.sosohandiary.exception.ErrorHandling.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
    private final FriendRepository friendRepository;

    @Transactional
    public MessageDto join(JoinRequestDto joinRequestDto) {

        String password = passwordEncoder.encode(joinRequestDto.getPassword());

        if (memberRepository.existsByEmail(joinRequestDto.getEmail())) {
            throw new ApiException(DUPLICATED_EMAIL);
        }

        if (memberRepository.existsByNickname(joinRequestDto.getNickname())) {
            throw new ApiException(DUPLICATED_NICKNAME);
        }

        MemberRoleEnum role = MemberRoleEnum.MEMBER;
        if (joinRequestDto.isAdmin()) {
            if (!joinRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new ApiException(INVALID_ADMIN_TOKEN);
            }
            role = MemberRoleEnum.ADMIN;
        }

        Member member = Member.of(joinRequestDto, password, role);
        memberRepository.save(member);

        return MessageDto.of("회원가입 성공", HttpStatus.ACCEPTED);
    }

    @Transactional
    public MemberMessageDto login(LoginRequestDto loginRequestDto, HttpServletResponse response) {

        Member member = memberRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(
                () -> new ApiException(NOT_FOUND_USER)
        );

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword())) {
            throw new ApiException(INVALID_PASSWORD);
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(loginRequestDto.getEmail(), member.getRole()));

        return MemberMessageDto.of("로그인 성공", HttpStatus.ACCEPTED, member);
    }

    @Transactional
    public List<MemberResponseDto> getMembersWithFriendStatus(String name, Member member) {

        List<Member> memberList = memberRepository.findByNameContaining(name);

        List<MemberResponseDto> responseDtoList = new ArrayList<>();

        for (Member eachMember : memberList) {
            List<Friend> friendList = friendRepository.findByMemberIdAndStatusOrderByFriendNicknameAsc(eachMember.getId(), StatusFriend.ACCEPTED);
            boolean isFriend = friendList.stream().anyMatch(friend -> friend.getFriend().getId().equals(member.getId()));
            boolean isPending = friendRepository.existsByFriend_IdAndMember_Id(member.getId(), eachMember.getId()) ||
                    friendRepository.existsByFriend_IdAndMember_Id(eachMember.getId(), member.getId());
            if (isFriend) {
                responseDtoList.add(MemberResponseDto.builder()
                        .id(eachMember.getId())
                        .name(eachMember.getName())
                        .nickname(eachMember.getNickname())
                        .statusMessage(eachMember.getStatusMessage())
                        .friendStatus(StatusFriend.ACCEPTED)
                        .build());
            } else if (isPending) {
                responseDtoList.add(MemberResponseDto.builder()
                        .id(eachMember.getId())
                        .name(eachMember.getName())
                        .nickname(eachMember.getNickname())
                        .statusMessage(eachMember.getStatusMessage())
                        .friendStatus(StatusFriend.PENDING)
                        .build());
            } else {
                responseDtoList.add(MemberResponseDto.builder()
                        .id(eachMember.getId())
                        .name(eachMember.getName())
                        .nickname(eachMember.getNickname())
                        .statusMessage(eachMember.getStatusMessage())
                        .friendStatus(null)
                        .build());
            }
        }
        return responseDtoList;
    }

}
