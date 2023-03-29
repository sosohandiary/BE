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

    @Column(nullable = false)
    private String imageURL;


    @Builder
    private Decoration(String imageURL) {
        this.imageURL = imageURL;
    }

    public static Decoration from(String imageURL) {
        return Decoration.builder()
                .imageURL(imageURL)
                .build();
    }
}
