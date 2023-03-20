package com.hanghae.sosohandiary.domain.decoration.controller;

import com.hanghae.sosohandiary.domain.decoration.entity.Decoration;
import com.hanghae.sosohandiary.domain.decoration.service.DecorationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class DecorationController {
    private final DecorationService decorationService;
    @PostMapping("/insert")
    public Decoration saveDecoration(@RequestBody String customJson){
        return decorationService.saveDecoration(customJson);
    }

//    @GetMapping("/insert/{id}")
//    public Decoration getDecoration(@PathVariable Long id,@RequestBody String customJson){
//        return decorationService.getDecoration(id, customJson);
//    }
}
