package com.inssa.server.api.image.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@NoArgsConstructor
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "image_no")
    private Long id;

    @Column(nullable = false)
    private String imgUrl;
    private String originFileName;

    public Image(String imgUrl, String originFileName) {
        this.originFileName = originFileName;
        this.imgUrl = imgUrl;
    }
}
