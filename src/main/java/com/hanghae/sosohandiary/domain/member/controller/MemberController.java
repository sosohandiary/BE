package com.hanghae.sosohandiary.domain.member.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hanghae.sosohandiary.domain.member.service.MemberService;
import com.hanghae.sosohandiary.utils.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/login")
    public MessageDto login(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        return memberService.login(code, response);
    }

}
