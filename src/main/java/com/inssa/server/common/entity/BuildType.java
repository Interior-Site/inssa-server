package com.inssa.server.common.entity;

import com.inssa.server.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 시공 종류: 방, 부엌, 거실, 베란다, 도배/장판, 기타
 */
@Getter
@NoArgsConstructor
@Entity
public class BuildType extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "build_type_no")
    private Long no;

    @Column(length = 10, nullable = false)
    private String name;
}
