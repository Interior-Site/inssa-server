package com.inssa.server.api.image.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

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
