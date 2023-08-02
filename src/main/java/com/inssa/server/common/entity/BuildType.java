package com.inssa.server.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 건물 유일: 아파트, 빌라, 오피스텔, 원룸/투룸, 카페, 식당, 기타
 */
@Getter
@NoArgsConstructor
@Entity
@Table(name = "build_type")
public class BuildType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "build_type_no")
    private Long no;

    @Column(length = 10, nullable = false)
    private String name;

    public BuildType(Long no, String name) {
        this.no = no;
        this.name = name;
    }
}
