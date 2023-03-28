package com.hanghae.sosohandiary.domain.like.controller;

import com.hanghae.sosohandiary.domain.like.dto.LikesResponseDto;
import com.hanghae.sosohandiary.domain.like.service.LikeService;
import com.hanghae.sosohandiary.security.MemberDetailsImpl;
import com.hanghae.sosohandiary.utils.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/detail/{detail-id}/like")
    public MessageDto postLike(@PathVariable(name = "detail-id") Long detailId,
                               @AuthenticationPrincipal MemberDetailsImpl memberDetails) {

        return likeService.postLike(detailId, memberDetails.getMember());
    }

    @GetMapping("/detail/{detail-id}/like")
    public List<LikesResponseDto> alarmLike(@PathVariable("detail-id") Long detailId,
                                            @AuthenticationPrincipal MemberDetailsImpl memberDetails) {
        return likeService.alarmLike(detailId, memberDetails.getMember());
    }

}
