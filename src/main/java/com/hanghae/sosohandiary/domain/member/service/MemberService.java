package com.hanghae.sosohandiary.domain.member.service;


import com.hanghae.sosohandiary.auth.JwtUtil;
import com.hanghae.sosohandiary.domain.friend.entity.Enum.StatusFriend;
import com.hanghae.sosohandiary.domain.friend.entity.Friend;
import com.hanghae.sosohandiary.domain.friend.repository.FriendRepository;
import com.hanghae.sosohandiary.domain.member.dto.JoinRequestDto;
import com.hanghae.sosohandiary.domain.member.dto.LoginRequestDto;
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
import java.util.Optional;

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

        String email = joinRequestDto.getEmail();
        String password = passwordEncoder.encode(joinRequestDto.getPassword());
        String name = joinRequestDto.getName();
        String nickname = joinRequestDto.getNickname();

        Optional<Member> foundEmail = memberRepository.findByEmail(email);
        if (foundEmail.isPresent()) {
            throw new ApiException(DUPLICATED_EMAIL);
        }

        Optional<Member> foundNickname = memberRepository.findByNickname(nickname);
        if(foundNickname.isPresent()) {
            throw new ApiException(DUPLICATED_NICKNAME);
        }

        MemberRoleEnum role = MemberRoleEnum.MEMBER;
        if (joinRequestDto.isAdmin()) {
            if (!joinRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new ApiException(INVALID_ADMIN_TOKEN);
            }
            role = MemberRoleEnum.ADMIN;
        }

        Member member = Member.of(email, password, name, nickname, role);
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

        return MessageDto.ofLogin("로그인 성공", HttpStatus.ACCEPTED, member.get());
    }

//    @Transactional
//    public List<MemberResponseDto> getMembers(String name) {
//
//        List<Member> memberList = memberRepository.findByNameContaining(name);
//
//        List<MemberResponseDto> responseDtoList = new ArrayList<>();
//
//        for(Member member : memberList) {
//            responseDtoList.add(MemberResponseDto.from(member));
//        }
//
//        return responseDtoList;
//    }

    @Transactional
    public List<MemberResponseDto> getMembersWithFriendStatus(String name, Long memberId) {
        List<Member> memberList = memberRepository.findByNameContaining(name);

        List<MemberResponseDto> responseDtoList = new ArrayList<>();

        for (Member member : memberList) {
            List<Friend> friendList = friendRepository.findByMemberIdAndStatusOrderByFriendNicknameAsc(member.getId(), StatusFriend.ACCEPTED);
            boolean isFriend = friendList.stream().anyMatch(friend -> friend.getFriend().getId().equals(memberId));
            boolean isPending = friendRepository.existsByFriend_IdAndMember_Id(memberId, member.getId()) || friendRepository.existsByFriend_IdAndMember_Id(member.getId(), memberId);

            if (isFriend) {
                responseDtoList.add(MemberResponseDto.builder()
                        .id(member.getId())
                        .name(member.getName())
                        .nickname(member.getNickname())
                        .statusMessage(member.getStatusMessage())
                        .friendStatus(StatusFriend.ACCEPTED)
                        .build());
            } else if (isPending) {
                responseDtoList.add(MemberResponseDto.builder()
                        .id(member.getId())
                        .name(member.getName())
                        .nickname(member.getNickname())
                        .statusMessage(member.getStatusMessage())
                        .friendStatus(StatusFriend.PENDING)
                        .build());
            } else {
                responseDtoList.add(MemberResponseDto.builder()
                        .id(member.getId())
                        .name(member.getName())
                        .nickname(member.getNickname())
                        .statusMessage(member.getStatusMessage())
                        .friendStatus(null)
                        .build());
            }
        }

        return responseDtoList;
    }



}
