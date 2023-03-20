package com.hanghae.sosohandiary.domain.decoration.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Decoration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String customJson;

    @Builder
    private Decoration(String customJson){
        this.customJson=customJson;
    }

    public static Decoration of(String customJson){
        return Decoration.builder()
                .customJson(customJson)
                .build();
    }
}
