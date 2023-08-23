package com.inssa.server.api.review.comment.service;

import com.inssa.server.api.review.comment.data.BuildReviewCommentRepository;
import com.inssa.server.api.review.comment.data.OrderReviewCommentRepository;
import com.inssa.server.api.review.comment.dto.ReviewCommentCreateResponseDto;
import com.inssa.server.api.review.comment.dto.ReviewCommentListResponseDto;
import com.inssa.server.api.review.comment.dto.ReviewCommentRequestDto;
import com.inssa.server.api.review.comment.model.BuildReviewComment;
import com.inssa.server.api.review.comment.model.OrderReviewComment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReviewCommentService {

    private final OrderReviewCommentRepository orderReviewCommentRepository;
    private final BuildReviewCommentRepository buildReviewCommentRepository;

    @Transactional(readOnly = true)
    public Page<ReviewCommentListResponseDto> findOrderReviewCommentsByReviewId(Long reviewNo, Pageable pageable) {
        Page<OrderReviewComment> comments = orderReviewCommentRepository.findByReviewNo(reviewNo, pageable);
        List<ReviewCommentListResponseDto> result = comments.stream().map(ReviewCommentListResponseDto::new).toList();
        return new PageImpl<>(result, comments.getPageable(), comments.getSize());
    }

    @Transactional(readOnly = true)
    public Page<ReviewCommentListResponseDto> findBuildReviewCommentsByReviewId(Long reviewNo, Pageable pageable) {
        Page<BuildReviewComment> comments = buildReviewCommentRepository.findByReviewNo(reviewNo, pageable);
        List<ReviewCommentListResponseDto> result = comments.stream().map(ReviewCommentListResponseDto::new).toList();
        return new PageImpl<>(result, comments.getPageable(), comments.getSize());
    }

    @Transactional
    public ReviewCommentCreateResponseDto createOrderReviewComment(
            Long reviewNo,
            ReviewCommentRequestDto request,
            Long userNo
    ) {
        return null;
    }
}
