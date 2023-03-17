package com.hanghae.sosohandiary.domain.myfriendsList.service;

import com.hanghae.sosohandiary.domain.member.entity.Member;
import com.hanghae.sosohandiary.domain.member.repository.MemberRepository;
import com.hanghae.sosohandiary.domain.myfriendsList.entity.FriendRequest;
import com.hanghae.sosohandiary.domain.myfriendsList.entity.MyFriendsList;
import com.hanghae.sosohandiary.domain.myfriendsList.repository.FriendRequestRepository;
import com.hanghae.sosohandiary.domain.myfriendsList.repository.MyFriendsListRepository;
import com.hanghae.sosohandiary.exception.ApiException;
import com.hanghae.sosohandiary.exception.ErrorHandling;
import com.hanghae.sosohandiary.utils.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyFriendsListService {
    private final MyFriendsListRepository myFriendsListRepository;
    private final FriendRequestRepository friendRequestRepository;
    private final MemberRepository memberRepository;


    public MessageDto createFriendRequest(Long id, Member member) {



        if (member.getId().equals(id)) {
            return new MessageDto("자기 자신을 추가할 수 없습니다.", HttpStatus.BAD_REQUEST);
        }

        Optional<Member> userId = memberRepository.findById(member.getId());
        Optional<Member> friendId = memberRepository.findById(id);

        Optional<FriendRequest> findUserId = friendRequestRepository.findById(userId.get().getId());


        if (findUserId.isPresent()) {
            if(findUserId.get().getFriend().getId().equals(id)){
                return new MessageDto("아직 친구가 수락하지 않았습니다.", HttpStatus.BAD_REQUEST);
            }
        }

        FriendRequest friendRequest = FriendRequest.builder()
                .member(userId.get())
                .friend(friendId.get())
                .build();

        friendRequestRepository.save(friendRequest);
        return new MessageDto("친구요청 성공", HttpStatus.CREATED);
    }
    @Transactional
    public MessageDto createFriendAccept(Long id, Member member) {
        Optional<Member> userId = memberRepository.findById(member.getId());
        Optional<Member> friendId = memberRepository.findById(id);


        MyFriendsList myFriendsList = MyFriendsList.builder()
                .member(userId.get())
                .friend(friendId.get())
                .build();


        MyFriendsList myFriendsList2 = MyFriendsList.builder()
                .member(friendId.get())
                .friend(userId.get())
                .build();

        myFriendsListRepository.save(myFriendsList);
        myFriendsListRepository.save(myFriendsList2);
        //todo : delete friendRequest 삭제로직
        return new MessageDto("친구추가 성공", HttpStatus.CREATED);
    }



}
