package com.hanghae.sosohandiary.domain.decoration.controller;

import com.hanghae.sosohandiary.domain.decoration.dto.DecorationResponseDto;
import com.hanghae.sosohandiary.domain.decoration.entity.Decoration;
import com.hanghae.sosohandiary.domain.decoration.service.DecorationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/decoration")
public class DecorationController {
    private final DecorationService decorationService;
    @PostMapping("/insert")
    public Decoration saveDecoration(@RequestBody String customJson){
        return decorationService.saveDecoration(customJson);
    }

    @GetMapping("/")
    public List<DecorationResponseDto> getDecoration(){
        return decorationService.getDecoration();
    }
}
