package com.inssa.server.api.review.order;

import com.inssa.server.api.review.order.dto.*;
import com.inssa.server.api.review.order.service.OrderService;
import com.inssa.server.api.user.model.AuthUser;
import com.inssa.server.common.annotation.PreAuthorizeLogInUser;
import com.inssa.server.common.response.InssaApiResponse;
import com.inssa.server.common.response.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

    // Article 컨벤션 X 질문용
    @Operation(summary = "견적 후기 등록", tags = "orderReview")
    @PreAuthorizeLogInUser
    @PostMapping
    public InssaApiResponse<OrderReviewCreateResponseDto> createOrderReview(
            @Valid @RequestBody OrderReviewCreateRequestDto createRequest,
            @AuthenticationPrincipal AuthUser user
    ) {
        OrderReviewCreateResponseDto createResponse = orderService.createOrderReview(createRequest, user.getUserNo());
        return InssaApiResponse.ok(ResponseCode.CREATED, createResponse);
    }

    // TODO: 미로그인 시 일부만 노출
    @Operation(summary = "견적 후기 목록 조회", tags = "orderReview")
    @GetMapping
    public InssaApiResponse<Page<OrderReviewListResponseDto>> findOrderReviews(
            @SortDefault(sort = "created_date", direction = Sort.Direction.DESC) Pageable pageable
            // TODO: 필터, 정렬, 검색 조건 등
    ) {
        return InssaApiResponse.ok(ResponseCode.SUCCESS, orderService.findOrderReviews(pageable));
    }

    // TODO: 미로그인 시 일부만 노출
    @Operation(summary = "견적 후기 상세 조회", tags = "orderReview")
    @GetMapping("/{orderReviewNo}")
    public InssaApiResponse<OrderReviewResponseDto> findById(
            @PathVariable Long orderReviewNo
    ) {
        OrderReviewResponseDto orderReviewResponse = orderService.findOrderReviewById(orderReviewNo);
        return InssaApiResponse.ok(ResponseCode.SUCCESS, orderReviewResponse);
    }


    // TODO
    @Operation(summary = "견적 후기 수정", tags = "orderReview")
    @PutMapping("/{orderReviewNo}")
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


    /* 질문용
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
