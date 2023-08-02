package com.inssa.server.api.review.order.service;

import com.inssa.server.api.review.order.data.OrderReviewRepository;
import com.inssa.server.api.review.order.dto.OrderReviewListResponseDto;
import com.inssa.server.api.review.order.dto.OrderReviewRequestDto;
import com.inssa.server.api.review.order.dto.OrderReviewResponseDto;
import com.inssa.server.api.review.order.model.OrderReview;
import com.inssa.server.common.exception.InssaException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("OrderService")
public class OrderService {

    private final OrderReviewRepository orderReviewRepository;

    public Long createOrderReview(OrderReviewRequestDto request) {
        OrderReview orderReview = orderReviewRepository.save(request.toEntity());
        return orderReview.getNo();
    }

    public Page<OrderReviewListResponseDto> findOrderReviews(Pageable pageable) {
        return orderReviewRepository.findOrderReviews(pageable);
    }

    public OrderReviewResponseDto findById(Long orderReviewNo) {
        return new OrderReviewResponseDto(orderReviewRepository.findById(orderReviewNo)
                .orElseThrow(() -> new InssaException("견적 후기가 존재하지 않습니다.")));
    }
}
