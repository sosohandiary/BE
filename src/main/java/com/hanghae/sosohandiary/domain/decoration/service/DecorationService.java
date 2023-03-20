package com.hanghae.sosohandiary.domain.decoration.service;

import com.hanghae.sosohandiary.domain.decoration.entity.Decoration;
import com.hanghae.sosohandiary.domain.decoration.repository.DecorationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DecorationService {
    private final DecorationRepository decorationRepository;
    public Decoration saveDecoration(String customJson) {
        Decoration decoration=decorationRepository.save(Decoration.of(customJson));
        return decoration;
    }

}
