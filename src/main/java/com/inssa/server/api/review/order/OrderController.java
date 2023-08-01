package com.inssa.server.api.review.order;

import com.inssa.server.api.review.order.dto.OrderReviewCreateRequestDto;
import com.inssa.server.api.review.order.dto.OrderReviewRequestDto;
import com.inssa.server.api.review.order.service.OrderService;
import com.inssa.server.api.user.model.AuthUser;
import com.inssa.server.common.exception.InssaException;
import com.inssa.server.common.response.InssaApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@Tag(name = "orderReview", description = "견적 후기 API")
@RequestMapping("/api/v1/review")
@RestController
public class OrderController {
    private final OrderService orderService;

    /**
     * Create
     * @return
     */
    @Operation(summary = "견적 후기 등록", tags = "orderReview")
    @PostMapping("/orders")
    public InssaApiResponse createOrderReview(
            @RequestBody OrderReviewCreateRequestDto createRequest,
            @AuthenticationPrincipal AuthUser user
    ){
        // user 판단
        if(user == null){
            throw new InssaException("로그인 후 이용 가능합니다.");
        }
        OrderReviewRequestDto requestDto = createRequest.toDto();
        return InssaApiResponse.ok(orderService.createOrderReview(requestDto));
    }

    /**
     * Read
     * @return
     */

    /**
     * Update
     * @return
     */

    /**
     * Delete
     * @return
     */

}
