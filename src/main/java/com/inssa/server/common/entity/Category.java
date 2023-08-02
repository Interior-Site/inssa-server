package com.inssa.server.common.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 시공 유형: 벽지, 도배, 장판, 샷시, 줄눈, 확장, 타일
 */
@Getter
@NoArgsConstructor
@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_no")
    private Long no;

    @Column(length = 10, name = "category_name", nullable = false)
    private String name;

    @Builder
    public Category(String name) {
        this.name = name;
    }

////    public Category(String name) {
////        this.name = name;
//    }
}
