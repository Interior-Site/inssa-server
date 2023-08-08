package com.inssa.server.api.review.order;

import com.inssa.server.api.review.filter.RequestParamObject;
import com.inssa.server.api.review.filter.ReviewFilterParam;
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
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@Tag(name = "orderReview", description = "견적 후기 API")
@RequestMapping("/api/v1/review/orders")
@RestController
public class OrderController {
    private final OrderService orderService;

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
            @Valid @ParameterObject ReviewFilterParam filter, // 필터
            @Valid @ParameterObject Pageable pageable
    ) {
        Page<OrderReviewListResponseDto> orderReviews = orderService.findOrderReviews(filter, pageable);
        return InssaApiResponse.ok(ResponseCode.SUCCESS, orderReviews);
    }

    // TODO: 베스트 시공 후기

    // TODO: 미로그인 시 일부만 노출
    @Operation(summary = "견적 후기 상세 조회", tags = "orderReview")
    @GetMapping("/{orderReviewNo}")
    public InssaApiResponse<OrderReviewResponseDto> findById(
            @PathVariable Long orderReviewNo
    ) {
        OrderReviewResponseDto orderReviewResponse = orderService.findOrderReviewById(orderReviewNo);
        return InssaApiResponse.ok(ResponseCode.SUCCESS, orderReviewResponse);
    }

    @Operation(summary = "견적 후기 수정", tags = "orderReview")
    @PutMapping
    public InssaApiResponse<OrderReviewUpdateResponseDto> updateOrderReview(
            @Valid @RequestBody OrderReviewUpdateRequestDto updateRequest,
            @AuthenticationPrincipal AuthUser user
    ) {
        OrderReviewRequestDto request = OrderReviewRequestDto.updateBuilder()
                .no(updateRequest.getNo())
                .amount(updateRequest.getAmount())
                .title(updateRequest.getTitle())
                .content(updateRequest.getContent())
                .companyNo(updateRequest.getCompanyNo())
                .buildTypes(updateRequest.getBuildTypes())
                .categories(updateRequest.getCategories())
                .userNo(user.getUserNo())
                .build();
        OrderReviewUpdateResponseDto updateResponse = orderService.updateOrderReview(request);
        return InssaApiResponse.ok(ResponseCode.UPDATED, updateResponse);
    }

    @Operation(summary = "견적 후기 삭제", tags = "orderReview")
    @PreAuthorizeLogInUser
    @DeleteMapping("/{orderReviewNo}")
    public InssaApiResponse<OrderReviewDeleteResponseDto> deleteOrderReview(
            @PathVariable Long orderReviewNo,
            @AuthenticationPrincipal AuthUser user
    ) {
        OrderReviewRequestDto request = OrderReviewRequestDto.deleteBuilder()
                .no(orderReviewNo)
                .userNo(user.getUserNo())
                .build();
        orderService.deleteOrderReview(request);
        return InssaApiResponse.ok(ResponseCode.DELETED, new OrderReviewDeleteResponseDto(orderReviewNo));
    }
}
