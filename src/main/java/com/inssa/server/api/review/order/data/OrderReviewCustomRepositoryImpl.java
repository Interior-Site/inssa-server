package com.inssa.server.api.review.order.data;

import com.inssa.server.api.review.order.dto.OrderReviewListResponseDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class OrderReviewCustomRepositoryImpl implements OrderReviewCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

//    @Override
//    public Page<OrderReviewListResponseDto> findOrderReviews(Pageable pageable) {
//        // TODO
//        return null;
//    }
}
