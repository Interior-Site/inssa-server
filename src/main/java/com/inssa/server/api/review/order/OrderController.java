package com.inssa.server.api.review.order;

import com.inssa.server.api.review.order.dto.*;
import com.inssa.server.api.review.order.service.OrderService;
import com.inssa.server.api.user.model.AuthUser;
import com.inssa.server.common.exception.InssaException;
import com.inssa.server.common.response.InssaApiResponse;
import com.inssa.server.common.response.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Tag(name = "orderReview", description = "견적 후기 API")
@RequestMapping("/api/v1/review/orders")
@RestController
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "견적 후기 등록", tags = "orderReview")
    @PostMapping
    public InssaApiResponse<Map<String, Object>> createOrderReview(
            @RequestBody OrderReviewCreateRequestDto createRequest,
            @AuthenticationPrincipal AuthUser user
    ) {
        // user 판단
        if (user == null) {
            throw new InssaException("로그인 후 이용 가능합니다.");
        }
        OrderReviewRequestDto requestDto = createRequest.toDto();
        Long orderReviewNo = orderService.createOrderReview(requestDto);
        // [header] Location: /api/v1/review/orders/{orderReviewNo}
        return InssaApiResponse.ok(ResponseCode.CREATED, Map.of("orderReviewNo", orderReviewNo));
    }

    @Operation(summary = "견적 후기 목록 조회", tags = "orderReview")
    @GetMapping
    public InssaApiResponse<Page<OrderReviewListResponseDto>> findOrderReviews(
            @SortDefault(sort = "created_date", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return InssaApiResponse.ok(ResponseCode.SUCCESS, orderService.findOrderReviews(pageable));
    }

    // TODO
    @Operation(summary = "견적 후기 단건 조회", tags = "orderReview")
    @GetMapping("/{orderReviewNo}")
    public InssaApiResponse<OrderReviewResponseDto> findById(
            @PathVariable Long orderReviewNo
    ) {
        return InssaApiResponse.ok(ResponseCode.SUCCESS, orderService.findById(orderReviewNo));
    }


    // TODO
    @Operation(summary = "견적 후기 수정", tags = "orderReview")
    @PatchMapping("/{orderReviewNo}")
    public InssaApiResponse<Map<String, Object>> updateOrderReview(
            @PathVariable Long orderReviewNo,
            @RequestBody OrderReviewUpdateRequestDto updateRequest
    ) {
        // updateRequest -> OrderReviewRequestDto
        // save
        // Long orderReviewNo
        return InssaApiResponse.ok(ResponseCode.UPDATED, Map.of("orderReviewNo", orderReviewNo));
    }


    // TODO
    @Operation(summary = "견적 후기 삭제", tags = "orderReview")
    @DeleteMapping("/{orderReviewNo}")
    public InssaApiResponse<Map<String, Object>> deleteOrderReview(
            @PathVariable Long orderReviewNo,
            @AuthenticationPrincipal AuthUser user
    ) {
        // orderService.deleteOrderReview
        return InssaApiResponse.ok(ResponseCode.DELETED, Map.of("orderReviewNo", orderReviewNo));
    }


    /*
    @Operation(summary = "견적 후기 삭제", tags = "orderReview")
    @DeleteMapping("/{orderReviewNo}")
    public InssaApiResponse<Void> deleteOrderReview(
            @PathVariable Long orderReviewNo,
            @AuthenticationPrincipal AuthUser user
    ) {
        // orderService.deleteOrderReview(orderReviewNo)
        return InssaApiResponse.ok(ResponseCode.DELETED);
    }
    */
}
