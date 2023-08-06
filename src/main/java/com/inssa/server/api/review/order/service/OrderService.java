package com.inssa.server.api.review.order.service;

import com.inssa.server.api.company.data.CompanyRepository;
import com.inssa.server.api.company.model.Company;
import com.inssa.server.api.review.filter.ReviewFilterParam;
import com.inssa.server.api.review.build_type.data.BuildTypeRepository;
import com.inssa.server.api.review.build_type.model.BuildType;
import com.inssa.server.api.review.category.data.CategoryRepository;
import com.inssa.server.api.review.category.model.Category;
import com.inssa.server.api.review.order.data.OrderReviewBuildTypeRepository;
import com.inssa.server.api.review.order.data.OrderReviewRepository;
import com.inssa.server.api.review.order.dto.*;
import com.inssa.server.api.review.order.model.OrderReview;
import com.inssa.server.api.review.order.data.OrderReviewCategoryRepository;
import com.inssa.server.api.review.order.model.OrderReviewBuildType;
import com.inssa.server.api.review.order.model.OrderReviewCategory;
import com.inssa.server.api.user.data.UserRepository;
import com.inssa.server.api.user.model.User;
import com.inssa.server.common.code.ErrorCode;
import com.inssa.server.common.exception.InssaException;
import com.inssa.server.share.board.BoardStatus;
import com.inssa.server.api.review.filter.ReviewSortOption;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service("OrderService")
public class OrderService {

    private final OrderReviewRepository orderReviewRepository;
    private final CompanyRepository companyRepository;
    private final BuildTypeRepository buildTypeRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final OrderReviewCategoryRepository orderReviewCategoryRepository;
    private final OrderReviewBuildTypeRepository orderReviewBuildTypeRepository;

    private OrderReview findById(Long orderReviewNo) {
        return orderReviewRepository.findById(orderReviewNo)
                .orElseThrow(() -> new InssaException("견적 후기가 존재하지 않습니다."));
    }

    private Company findCompanyById(Long companyNo) {
        return companyRepository.findById(companyNo)
                .orElseThrow(() -> new InssaException(ErrorCode.NOT_FOUND, "업체가 존재하지 않습니다."));
    }

    // 전체 건물 유형
    private Map<Long, BuildType> findAllBuildTypes() {
        return buildTypeRepository.findAll()
                .stream().collect(Collectors.toMap(BuildType::getNo, v -> v));
    }

    // 전체 시공 유형
    private Map<Long, Category> findAllCategories() {
        return categoryRepository.findAll()
                .stream().collect(Collectors.toMap(Category::getNo, v -> v));
    }


    private void validateAuthority(Long requestUserNo, Long userNo) {
        // TODO: 관리자인지 검사
        findUserById(userNo);
        // 동일인인지 검사
        if (!Objects.equals(requestUserNo, userNo)){
            throw new InssaException(ErrorCode.FORBIDDEN, "권한이 없습니다.");
        }
    }

    private User findUserById(Long userNo) {
        return userRepository.findById(userNo)
                .orElseThrow(() -> new InssaException(ErrorCode.NOT_FOUND, "사용자가 존재하지 않습니다"));
    }

    private void validateCategories(List<Long> categoryIds, Map<Long, Category> availableCategoryIds) {
        categoryIds.forEach(id -> {
            if (!availableCategoryIds.containsKey(id)) {
                throw new InssaException(ErrorCode.INVALID, "시공 유형 번호가 올바르지 않습니다.");
            }
        });
    }

    private void validateBuildTypes(List<Long> buildTypeIds, Map<Long, BuildType> availableBuildTypeIds) {
        buildTypeIds.forEach(id -> {
            if (!availableBuildTypeIds.containsKey(id)) {
                throw new InssaException(ErrorCode.INVALID, "건물 유형 번호가 올바르지 않습니다.");
            }
        });
    }

    private void validateStatus(BoardStatus status) {
        if (!status.isVisible()) {
            throw new InssaException(ErrorCode.NOT_FOUND, "견적 후기를 찾을 수 없습니다.");
        }
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

        // 사용 가능한 시공유형, 건물유형을 캐시에서 조회
        Map<Long, BuildType> allBuildTypes = findAllBuildTypes();
        Map<Long, Category> allCategories = findAllCategories();

        // 시공 유형 & 건물 유형 검사
        List<Long> categories = request.getCategories();
        List<Long> buildTypes = request.getBuildTypes();

        validateCategories(categories, allCategories);
        validateBuildTypes(buildTypes, allBuildTypes);

        // 견적 후기
        OrderReview orderReview = request.toEntity(userNo, company.getNo());

        // 시공 유형 연결
        categories.stream()
                .map(category -> OrderReviewCategory.builder()
                        .orderReview(orderReview)
                        .category(allCategories.get(category))
                        .build())
                .forEach(orderReview::addReviewCategory);

        // 건물 유형 연결
        buildTypes.stream()
                .map(buildType -> OrderReviewBuildType.builder()
                        .orderReview(orderReview)
                        .buildType(allBuildTypes.get(buildType))
                        .build())
                .forEach(orderReview::addReviewBuildType);

        // 견적 후기 등록
        return new OrderReviewCreateResponseDto(orderReviewRepository.save(orderReview));
    }

    /**
     * 페이지별 견적 후기 목록 조회
     * (추후 QueryDSL로 리팩토링)
     * @param pageable: 페이지네이션 객체
     * @return 페이지 범위 내 견적 후기 목록
     */
    @Transactional(readOnly = true)
    public Page<OrderReviewListResponseDto> findOrderReviews(
            ReviewFilterParam filter,
            Pageable pageable
    ) {
        // 정렬 옵션
        ReviewSortOption sortBy = ReviewSortOption.fromValue(filter.getSortBy());
        Sort sort = Sort.by("createdAt").descending();
        if (sortBy != null) {
            switch (sortBy) {
                case COMMENTS -> sort = Sort.by("comments").descending();
                case LIKES -> sort = Sort.by("likes").descending();
                case AMOUNT_HIGH_TO_LOW -> sort = Sort.by("amount").descending();
                case AMOUNT_LOW_TO_HIGH -> sort = Sort.by("amount").ascending();
                default -> sort = Sort.by("createdAt").descending();
            }
        }
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        // 검색 키워드
        String keyword = filter.getKeyword();

        // 필터
        List<Long> buildTypeIds = filter.getBuildTypes();
        List<Long> categoryIds = filter.getCategories();

        // 사용 가능한 시공유형, 건물유형을 캐시에서 조회
        Map<Long, BuildType> availableBuildTypes = findAllBuildTypes();
        Map<Long, Category> availableCategories = findAllCategories();

        // 건물 유형, 시공유형 검증
        validateBuildTypes(buildTypeIds, availableBuildTypes);
        validateCategories(categoryIds, availableCategories);

        // 접근 가능한 글만 조회
        BoardStatus status = BoardStatus.VISIBLE;

        // 필터, 검색 키워드, 상태, 정렬 조건 적용하여 데이터 조회
        Page<OrderReview> orderReviews = orderReviewRepository.findFilteredAndSortedOrderReviews(
                status,
                keyword,
                buildTypeIds,
                categoryIds,
                pageRequest
        );
        return orderReviews.map(OrderReviewListResponseDto::new);
    }

    /**
     * 견적 후기 상세 조회 (PK, 제목, 내용, 작성자, 업체, 상태, 조회수, 시공유형, 건물유형)
     * @param orderReviewNo: 견적 후기 PK
     * @return 견적 후기 상세 정보
     */
    @Transactional
    public OrderReviewResponseDto findOrderReviewById(Long orderReviewNo) {
        // 견적 후기 조회
        OrderReview orderReview = findById(orderReviewNo);

        // BoardStatus로 접근 가능 여부 조회
        if (!orderReview.getStatus().isVisible()){
            throw new InssaException(ErrorCode.NOT_FOUND, "견적 후기를 찾을 수 없습니다.");
        }
        orderReview.increaseViewCount();
        Company company = orderReview.getCompany();
        List<BuildType> buildTypes = orderReview.getBuildTypes();
        List<Category> categories = orderReview.getCategories();
        return new OrderReviewResponseDto(orderReview, company, buildTypes, categories);
    }

    /**
     * 견적 후기 수정
     * @param request: 견적 후기 수정 요청 정보(PK, 금액, 제목, 내용, 업체PK, 시공유형, 건물유형)
     * @return 견적 후기 수정 정보 - 견적 후기 PK
     */
    @Transactional
    public OrderReviewUpdateResponseDto updateOrderReview(OrderReviewRequestDto request) {
        OrderReview orderReview = findById(request.getNo());

        validateAuthority(request.getUserNo(), orderReview.getUserNo());
        validateStatus(orderReview.getStatus());

        // 시공 유형, 건물 유형 유효성 확인
        List<Long> newbuildTypeIds = request.getBuildTypes();
        List<Long> newCategoryIds = request.getCategories();

        validateBuildTypes(newbuildTypeIds, findAllBuildTypes());
        validateCategories(newCategoryIds, findAllCategories());

        // 업체 유효성 확인
        Company company = findCompanyById(request.getCompanyNo());

        // 건물 유형 - 전체 수정
        List<OrderReviewBuildType> reviewBuildTypes = newbuildTypeIds.stream()
                .map(id -> OrderReviewBuildType.builder()
                        .orderReview(orderReview)
                        .buildType(findAllBuildTypes().get(id))
                        .build()
                ).toList();

        // 시공 유형 - 전체 수정
        List<OrderReviewCategory> reviewCategories = newCategoryIds.stream()
                .map(id -> OrderReviewCategory.builder()
                        .orderReview(orderReview)
                        .category(findAllCategories().get(id)).build()
                ).toList();

        OrderReview updatedReview = orderReview.updateOrderReview(
                request.getAmount(),
                request.getTitle(),
                request.getContent(),
                company,
                reviewBuildTypes,
                reviewCategories
        );
        return new OrderReviewUpdateResponseDto(updatedReview);
    }

    @Transactional
    public void deleteOrderReview(OrderReviewRequestDto request) {
        OrderReview orderReview = findById(request.getNo());
        validateAuthority(request.getUserNo(), orderReview.getUserNo());
        orderReviewRepository.deleteById(orderReview.getNo());
    }
}