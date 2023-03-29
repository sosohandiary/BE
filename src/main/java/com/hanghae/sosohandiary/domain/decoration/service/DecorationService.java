package com.hanghae.sosohandiary.domain.decoration.service;

import com.hanghae.sosohandiary.domain.decoration.dto.DecorationResponseDto;
import com.hanghae.sosohandiary.domain.decoration.entity.Decoration;
import com.hanghae.sosohandiary.domain.decoration.repository.DecorationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DecorationService {

    private final DecorationRepository decorationRepository;

    @Transactional
    public Decoration saveDecoration(String imageURL) {
        return decorationRepository.save(Decoration.from(imageURL));
    }

    public List<DecorationResponseDto> getDecoration() {

        List<Decoration> decorationList = decorationRepository.findAll();
        List<DecorationResponseDto> decorationResponseDtoList = new ArrayList<>();

        for (Decoration decoration : decorationList) {
            decorationResponseDtoList.add(DecorationResponseDto.of(decoration.getId(), decoration.getImageURL()));
        }

        return decorationResponseDtoList;
    }
}
