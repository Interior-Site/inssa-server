package com.inssa.server.api.review.order.data;

import com.inssa.server.api.review.order.dto.OrderReviewListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderReviewCustomRepository {
    Page<OrderReviewListResponseDto> findOrderReviews(Pageable pageable);
}
