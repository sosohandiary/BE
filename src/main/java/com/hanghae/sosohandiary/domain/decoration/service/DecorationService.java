package com.hanghae.sosohandiary.domain.decoration.service;

import com.hanghae.sosohandiary.domain.decoration.entity.Decoration;
import com.hanghae.sosohandiary.domain.decoration.repository.DecorationRepository;
import com.hanghae.sosohandiary.exception.ApiException;
import com.hanghae.sosohandiary.exception.ErrorHandling;
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

    public Decoration getDecoration(Long id) {
        Decoration decoration=decorationRepository.findById(id).orElseThrow(
                () -> new ApiException(ErrorHandling.NOT_FOUND_DIARY)
        );
        return decoration;
    }
}
