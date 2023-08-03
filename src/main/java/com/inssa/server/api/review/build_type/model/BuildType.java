package com.inssa.server.api.review.build_type.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 건물 유형: 아파트, 빌라, 오피스텔, 원룸/투룸, 카페, 식당, 기타
 */
@Getter
@NoArgsConstructor
@Entity
public class BuildType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "build_type_no")
    private Long no;

    @Column(length = 10, nullable = false)
    private String name;

    @Builder
    public BuildType(String name) {
        this.name = name;
    }
}
