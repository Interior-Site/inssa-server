package com.inssa.server.api.review.comment.service;

import com.inssa.server.api.image.dto.ImageResponseDto;
import com.inssa.server.api.image.model.Image;
import com.inssa.server.api.review.comment.data.BuildReviewCommentLikeRepository;
import com.inssa.server.api.review.comment.data.BuildReviewCommentRepository;
import com.inssa.server.api.review.comment.data.OrderReviewCommentLikeRepository;
import com.inssa.server.api.review.comment.data.OrderReviewCommentRepository;
import com.inssa.server.api.review.comment.dto.*;
import com.inssa.server.api.review.comment.model.BuildReviewComment;
import com.inssa.server.api.review.comment.model.OrderReviewComment;
import com.inssa.server.api.user.data.UserRepository;
import com.inssa.server.api.user.model.User;
import com.inssa.server.common.code.ErrorCode;
import com.inssa.server.common.exception.InssaException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReviewCommentService {

    private final OrderReviewCommentRepository orderReviewCommentRepository;
    private final BuildReviewCommentRepository buildReviewCommentRepository;
    private final OrderReviewCommentLikeRepository orderReviewCommentLikeRepository;
    private final BuildReviewCommentLikeRepository buildReviewCommentLikeRepository;
    private final UserRepository userRepository;

    private ReviewUserResponseDto getUserResponse(User user) {
        Image image = user.getProfile();
        ImageResponseDto profile = (Objects.nonNull(image))? new ImageResponseDto(image.getId(), image.getImgUrl(), image.getOriginFileName()): null;
        return new ReviewUserResponseDto(user.getNo(), user.getNickname(), user.getStatus(), profile, user.getRole());
    }

    private ReviewCommentResponseDto getOrderReviewCommentResponse(OrderReviewComment comment, Long currentUserNo) {
        boolean hasLiked = orderReviewCommentLikeRepository.existsByUserNoAndReviewCommentNo(currentUserNo, comment.getNo());
        return new ReviewCommentResponseDto(
                comment.getNo(),
                comment.getContent(),
                getUserResponse(comment.getUser()),
                comment.getCreatedDate(),
                comment.getModifiedDate(),
                new ReviewCommentLikeResponseDto(hasLiked, comment.getLikeCount())
        );
    }

    private ReviewCommentListResponseDto getOrderReviewCommentListResponse(OrderReviewComment comment, Long currentUserNo) {
        boolean hasLiked = orderReviewCommentLikeRepository.existsByUserNoAndReviewCommentNo(currentUserNo, comment.getNo());
        return new ReviewCommentListResponseDto(
                comment.getNo(),
                comment.getContent(),
                getUserResponse(comment.getUser()),
                comment.getCreatedDate(),
                comment.getModifiedDate(),
                new ReviewCommentLikeResponseDto(hasLiked, comment.getLikeCount()),
                comment.getChildren().stream().map(child -> getOrderReviewCommentResponse(child, currentUserNo)).toList()
        );
    }

    private ReviewCommentResponseDto getBuildReviewCommentResponse(BuildReviewComment comment, Long currentUserNo) {
        boolean hasLiked = buildReviewCommentLikeRepository.existsByUserNoAndReviewCommentNo(currentUserNo, comment.getNo());
        return new ReviewCommentResponseDto(
                comment.getNo(),
                comment.getContent(),
                getUserResponse(comment.getUser()),
                comment.getCreatedDate(),
                comment.getModifiedDate(),
                new ReviewCommentLikeResponseDto(hasLiked, comment.getLikeCount())
        );
    }

    private ReviewCommentListResponseDto getBuildReviewCommentListResponse(BuildReviewComment comment, Long currentUserNo) {
        boolean hasLiked = buildReviewCommentLikeRepository.existsByUserNoAndReviewCommentNo(currentUserNo, comment.getNo());
        return new ReviewCommentListResponseDto(
                comment.getNo(),
                comment.getContent(),
                getUserResponse(comment.getUser()),
                comment.getCreatedDate(),
                comment.getModifiedDate(),
                new ReviewCommentLikeResponseDto(hasLiked, comment.getLikeCount()),
                comment.getChildren().stream().map(child -> getBuildReviewCommentResponse(child, currentUserNo)).toList()
        );
    }

    /**
     * 견적후기 댓글 목록 API
     * @param reviewNo: 견적후기 PK
     * @param pageable: page 객체
     * @param userNo: 로그인한 사용자 PK
     * @return 견적후기 댓글 목록 Page response
     */
    @Transactional(readOnly = true)
    public Page<ReviewCommentListResponseDto> findOrderReviewCommentsByReviewId(Long reviewNo, Pageable pageable, Long userNo) {
        Page<OrderReviewComment> comments = orderReviewCommentRepository.findByParentNullAndDeletedFalseAndReviewNo(reviewNo, pageable);
        List<ReviewCommentListResponseDto> result = comments.stream()
                .map(comment -> getOrderReviewCommentListResponse(comment, userNo)).toList();
        return new PageImpl<>(result, comments.getPageable(), comments.getSize());
    }

    /**
     * 시공후기 댓글 목록 API
     * @param reviewNo: 시공후기 PK
     * @param pageable: page 객체
     * @param userNo: 로그인한 사용자 PK
     * @return 시공후기 댓글 목록 Page response
     */
    @Transactional(readOnly = true)
    public Page<ReviewCommentListResponseDto> findBuildReviewCommentsByReviewId(Long reviewNo, Pageable pageable, Long userNo) {
        Page<BuildReviewComment> comments = buildReviewCommentRepository.findByParentNullAndDeletedFalseAndReviewNo(reviewNo, pageable);
        List<ReviewCommentListResponseDto> result = comments.stream()
                .map(comment -> getBuildReviewCommentListResponse(comment, userNo)).toList();
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
