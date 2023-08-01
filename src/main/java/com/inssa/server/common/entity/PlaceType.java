package com.inssa.server.common.entity;

import com.inssa.server.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 시공 건물: 아파트, 빌라, 오피스텔, 원룸/투룸, 카페, 식당, 기타
 */
@Getter
@NoArgsConstructor
@Entity
public class PlaceType extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_type_no")
    private Long no;

    @Column(length = 10, nullable = false)
    private String name;
}
