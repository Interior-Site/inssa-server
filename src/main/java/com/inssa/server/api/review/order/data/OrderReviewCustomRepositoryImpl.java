package com.inssa.server.api.review.order.data;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderReviewCustomRepositoryImpl implements OrderReviewCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;
}
