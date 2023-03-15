package com.hanghae.sosohandiary.domain.image.entity;

import com.hanghae.sosohandiary.domain.diarydetail.entity.DiaryDetail;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String uploadPath;

    @ManyToOne
    @JoinColumn(name = "diary_id", nullable = false)
    private DiaryDetail diary;

    @Builder
    private Image(String uploadPath, DiaryDetail diary) {
        this.uploadPath = uploadPath;
        this.diary = diary;
    }

    public static Image of(String uploadPath, DiaryDetail diary) {
        return Image.builder()
                .uploadPath(uploadPath)
                .diary(diary)
                .build();
    }
}
