package com.inssa.server.api.review.order.service;

import com.inssa.server.api.review.order.data.OrderReviewRepository;
import com.inssa.server.api.review.order.dto.OrderReviewRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("OrderService")
public class OrderService {

    private final OrderReviewRepository orderReviewRepository;

    public Object createOrderReview(OrderReviewRequestDto request) {
        return null;
    }
}
