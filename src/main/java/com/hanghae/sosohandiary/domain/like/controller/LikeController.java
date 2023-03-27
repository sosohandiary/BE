package com.hanghae.sosohandiary.domain.like.controller;

import com.hanghae.sosohandiary.domain.like.service.LikeService;
import com.hanghae.sosohandiary.security.MemberDetailsImpl;
import com.hanghae.sosohandiary.utils.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;
    @PostMapping("{diary-id}/detail/{detail-id}/like")
    public MessageDto postLike(@PathVariable(name = "diary-id") Long diaryId,@PathVariable(name = "detail-id") Long detailId, @AuthenticationPrincipal MemberDetailsImpl memberDetails) {

        return likeService.postLike(diaryId, detailId, memberDetails.getMember());
    }

}
