package com.inssa.server.api.review.order.data;

import com.inssa.server.api.review.order.model.OrderReview;
import com.inssa.server.share.board.BoardStatus;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Objects;

public interface OrderReviewRepository extends JpaRepository<OrderReview, Long>, OrderReviewCustomRepository {

    default Page<OrderReview> findFilteredAndSortedOrderReviews(
            final BoardStatus status,
            final String keyword,
            final List<Long> buildTypeIds,
            final List<Long> categoryIds,
            final Pageable pageable
    ) {
        BoardStatus boardStatus = Objects.isNull(status)? BoardStatus.VISIBLE: status;
        return findAll((root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("status"), boardStatus));
            if (Objects.nonNull(keyword) && !keyword.isBlank()){
                Predicate keywordPredicate = criteriaBuilder.or(
                        criteriaBuilder.like(root.get("title"), "%" + keyword + "%"),
                        criteriaBuilder.like(root.get("content"), "%" + keyword + "%")
                );
                predicate = criteriaBuilder.and(predicate, keywordPredicate);
            }
            if (Objects.nonNull(buildTypeIds) && !buildTypeIds.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, root.join("orderReviewBuildTypes").get("build_type_no").in(buildTypeIds));
            }
            if (Objects.nonNull(categoryIds) && !categoryIds.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, root.join("orderReviewCategories").get("category_no").in(categoryIds));
            }
            return predicate;
        }, pageable);
    }

    Page<OrderReview> findAll(Specification<OrderReview> spec, Pageable pageable);
}
