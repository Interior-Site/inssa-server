package com.inssa.server.api.review.order.service;

import com.inssa.server.api.company.data.CompanyRepository;
import com.inssa.server.api.company.model.Company;
import com.inssa.server.api.review.order.data.OrderReviewRepository;
import com.inssa.server.api.review.order.dto.OrderReviewCreateResponseDto;
import com.inssa.server.api.review.order.dto.OrderReviewListResponseDto;
import com.inssa.server.api.review.order.dto.OrderReviewCreateRequestDto;
import com.inssa.server.api.review.order.dto.OrderReviewResponseDto;
import com.inssa.server.api.review.order.model.OrderReview;
import com.inssa.server.common.code.ErrorCode;
import com.inssa.server.common.exception.InssaException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service("OrderService")
public class OrderService {

    private final OrderReviewRepository orderReviewRepository;
    private final CompanyRepository companyRepository;

    private OrderReview findById(Long orderReviewNo) {
        return orderReviewRepository.findById(orderReviewNo)
                .orElseThrow(() -> new InssaException("견적 후기가 존재하지 않습니다."));
    }

    private Company findCompanyById(Long companyNo) {
        return companyRepository.findById(companyNo)
                .orElseThrow(() -> new InssaException(ErrorCode.NOT_FOUND, "업체가 존재하지 않습니다."));
    }

    /**
     * 견적 후기 등록
     * @param request: 견적 후기 등록 요청 정보(제목, 내용, 업체, 금액, 이미지)
     * @param userNo: 사용자 PK
     * @return 견적 후기 등록 결과
     */
    @Transactional
    public OrderReviewCreateResponseDto createOrderReview(OrderReviewCreateRequestDto request, Long userNo) {
        // 업체 조회
        Company company = findCompanyById(request.getCompanyNo());

        // 시공 유형 조회
        // 시공 유형 연결 (중간테이블 등록)

        // 건물 유형 조회
        // 건물 유형 연결 (중간테이블 등록)

        // 견적 후기 등록
        OrderReview orderReview = orderReviewRepository.save(request.toEntity(userNo, company.getNo()));
        return new OrderReviewCreateResponseDto(orderReview);
    }

    /**
     * 페이지별 견적 후기 목록 조회
     * @param pageable: 페이지네이션 객체
     * @return 페이지 범위 내 견적 후기 목록
     */
    public Page<OrderReviewListResponseDto> findOrderReviews(
            Pageable pageable
            // TODO: 정럴 & 필터 & 검색 조건
    ) {
        // 추후 QueryDSL로 리팩토링
        // TODO: 검색, 정렬, 필터 조건 추가
        Page<OrderReview> page = orderReviewRepository.findAll(pageable);
        if (Objects.isNull(page)) {
            throw new InssaException(ErrorCode.INTERNAL_SERVER_ERROR, "페이지 응답 파라미터가 누락되었습니다.");
        }
        List<OrderReviewListResponseDto> orderReviews = page.stream()
                .map(OrderReviewListResponseDto::new)
                .toList();
        return new PageImpl<>(orderReviews, pageable, page.getTotalElements());
    }

    /**
     * 견적 후기 상세 조회 (PK, 제목, 내용, 작성자, 업체, 상태, 조회수, 시공유형, 건물유형)
     * @param orderReviewNo: 견적 후기 PK
     * @return 견적 후기 상세 정보
     */
    public OrderReviewResponseDto findOrderReviewById(Long orderReviewNo) {
        // 견적 후기 상태 검사
        // 연결된 시공유형 조회
        // 연결된 건물 유형 조회
        return new OrderReviewResponseDto(findById(orderReviewNo));
    }
}
