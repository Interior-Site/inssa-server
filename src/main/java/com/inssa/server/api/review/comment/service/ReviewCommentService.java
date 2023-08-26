package com.inssa.server.api.review.comment.service;

import com.inssa.server.api.image.dto.ImageResponseDto;
import com.inssa.server.api.image.model.Image;
import com.inssa.server.api.review.build.data.BuildReviewRepository;
import com.inssa.server.api.review.build.model.BuildReview;
import com.inssa.server.api.review.comment.data.BuildReviewCommentLikeRepository;
import com.inssa.server.api.review.comment.data.BuildReviewCommentRepository;
import com.inssa.server.api.review.comment.data.OrderReviewCommentLikeRepository;
import com.inssa.server.api.review.comment.data.OrderReviewCommentRepository;
import com.inssa.server.api.review.comment.dto.*;
import com.inssa.server.api.review.comment.model.BuildReviewComment;
import com.inssa.server.api.review.comment.model.OrderReviewComment;
import com.inssa.server.api.review.order.data.OrderReviewRepository;
import com.inssa.server.api.review.order.model.OrderReview;
import com.inssa.server.api.user.data.UserRepository;
import com.inssa.server.api.user.model.User;
import com.inssa.server.common.code.ErrorCode;
import com.inssa.server.common.exception.InssaException;
import com.inssa.server.share.board.BoardStatus;
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

    private final OrderReviewRepository orderReviewRepository;
    private final BuildReviewRepository buildReviewRepository;
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

    private OrderReview findAndValidateOrderReviewByNo(Long reviewNo) {
        return orderReviewRepository.findByStatusAndNo(BoardStatus.VISIBLE, reviewNo)
                .orElseThrow(() -> new InssaException(ErrorCode.NOT_FOUND, "견적 후기를 찾을 수 없습니다."));
    }

    private OrderReviewComment findAndValidateOrderReviewCommentByParentNo(Long parentNo) {
        return orderReviewCommentRepository.findByDeletedFalseAndNo(parentNo)
                .orElseThrow(() -> new InssaException(ErrorCode.NOT_FOUND, "상위 댓글을 찾을 수 없습니다."));
    }

    private OrderReviewComment findAndValidateOrderReviewCommentByNo(Long commentNo) {
        return orderReviewCommentRepository.findByDeletedFalseAndNo(commentNo)
                .orElseThrow(() -> new InssaException(ErrorCode.NOT_FOUND, "댓글을 찾을 수 없습니다."));
    }

    private BuildReview findAndValidateBuildReviewByNo(Long reviewNo) {
        return buildReviewRepository.findByStatusAndNo(BoardStatus.VISIBLE, reviewNo)
                .orElseThrow(() -> new InssaException(ErrorCode.NOT_FOUND, "시공 후기를 찾을 수 없습니다."));
    }

    private BuildReviewComment findAndValidateBuildReviewCommentByParentNo(Long parentNo) {
        return buildReviewCommentRepository.findByDeletedFalseAndNo(parentNo)
                .orElseThrow(() -> new InssaException(ErrorCode.NOT_FOUND, "상위 댓글을 찾을 수 없습니다."));
    }

    private BuildReviewComment findAndValidateBuildReviewCommentByNo(Long commentNo) {
        return buildReviewCommentRepository.findByDeletedFalseAndNo(commentNo)
                .orElseThrow(() -> new InssaException(ErrorCode.NOT_FOUND, "댓글을 찾을 수 없습니다."));
    }

    private User findAndValidateUserByNo(Long userNo) {
        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new InssaException(ErrorCode.UNAUTHORIZED, "사용자를 찾을 수 없습니다."));
        if (!user.isActive()) {
            throw new InssaException(ErrorCode.FORBIDDEN, "댓글 작성 권한이 없습니다.");
        }
        return user;
    }

    private void validateAuthority(Long modelUserNo, Long requestUserNo) {
        if (Objects.isNull(requestUserNo)) {
            throw new InssaException(ErrorCode.UNAUTHORIZED, "로그인이 필요합니다.");
        }
        // TODO: 관리자 여부 확인
        if (!Objects.equals(modelUserNo, requestUserNo)) {
            throw new InssaException(ErrorCode.FORBIDDEN, "권한이 없습니다.");
        }
        findAndValidateUserByNo(requestUserNo);
    }


    /**
     * 견적후기 댓글 등록 API
     * @param request: 견적후기 댓글 요청 정보
     * @return 생성된 견적후기 댓글 PK
     */
    @Transactional
    public ReviewCommentResponseDto createOrderReviewComment(ReviewCommentRequestDto request) {
        User user = findAndValidateUserByNo(request.getUserNo());
        OrderReview review = findAndValidateOrderReviewByNo(request.getReviewNo());
        OrderReviewComment parentComment = (Objects.nonNull(request.getParentNo()))? findAndValidateOrderReviewCommentByParentNo(request.getParentNo()): null;
        OrderReviewComment comment = orderReviewCommentRepository.save(
                OrderReviewComment.builder()
                        .user(user)
                        .review(review)
                        .parent(parentComment)
                        .content(request.getContent())
                        .build());
        return new ReviewCommentResponseDto(comment.getNo());
    }


    /**
     * 시공후기 댓글 등록 API
     * @param request: 시공후기 댓글 요청 정보
     * @return 생성된 시공후기 댓글 PK
     */
    @Transactional
    public ReviewCommentResponseDto createBuildReviewComment(ReviewCommentRequestDto request) {
        User user = findAndValidateUserByNo(request.getUserNo());
        BuildReview review = findAndValidateBuildReviewByNo(request.getReviewNo());
        BuildReviewComment parentComment = (Objects.nonNull(request.getParentNo()))? findAndValidateBuildReviewCommentByParentNo(request.getParentNo()): null;
        BuildReviewComment comment = buildReviewCommentRepository.save(
                BuildReviewComment.builder()
                        .user(user)
                        .review(review)
                        .parent(parentComment)
                        .content(request.getContent())
                        .build());
        return new ReviewCommentResponseDto(comment.getNo());
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

    /**
     * 견적후기 댓글 수정 API
     * @param request: 견적후기 댓글 요청 정보
     * @return 수정된 견적후기 댓글 PK
     */
    @Transactional
    public ReviewCommentResponseDto updateOrderReviewComment(ReviewCommentRequestDto request) {
        OrderReviewComment comment = findAndValidateOrderReviewCommentByNo(request.getCommentNo());
        if (Objects.nonNull(request.getParentNo())) {
            // TODO: 상위 댓글 삭제 여부에 따른 처리
            findAndValidateOrderReviewCommentByParentNo(request.getParentNo());
        }
        validateAuthority(comment.getUser().getNo(), request.getUserNo());
        comment.updateContent(request.getContent());
        return new ReviewCommentResponseDto(comment.getNo());
    }

    /**
     * 시공후기 댓글 수정 API
     * @param request: 견적후기 댓글 요청 정보
     * @return 수정된 견적후기 댓글 PK
     */
    @Transactional
    public ReviewCommentResponseDto updateBuildReviewComment(ReviewCommentRequestDto request) {
        BuildReviewComment comment = findAndValidateBuildReviewCommentByNo(request.getCommentNo());
        if (Objects.nonNull(request.getParentNo())) {
            // TODO: 상위 댓글 삭제 여부에 따른 처리
            findAndValidateBuildReviewCommentByParentNo(request.getParentNo());
        }
        validateAuthority(comment.getUser().getNo(), request.getUserNo());
        comment.updateContent(request.getContent());
        return new ReviewCommentResponseDto(comment.getNo());
    }

    /**
     * 견적후기 댓글 삭제 API
     * @param request 견적후기 댓글 삭제 요청 정보
     * @return 삭제된 견적후기 댓글 PK
     */
    @Transactional
    public ReviewCommentResponseDto deleteOrderReviewComment(ReviewCommentRequestDto request) {
        OrderReviewComment comment = findAndValidateOrderReviewCommentByNo(request.getCommentNo());
        validateAuthority(comment.getUser().getNo(), request.getUserNo());
        comment.delete();
        return new ReviewCommentResponseDto(comment.getNo());
    }

    /**
     * 시공후기 댓글 삭제 API
     * @param request 시공후기 댓글 삭제 요청 정보
     * @return 삭제된 시공후기 댓글 PK
     */
    @Transactional
    public ReviewCommentResponseDto deleteBuildReviewComment(ReviewCommentRequestDto request) {
        BuildReviewComment comment = findAndValidateBuildReviewCommentByNo(request.getCommentNo());
        validateAuthority(comment.getUser().getNo(), request.getUserNo());
        comment.delete();
        return new ReviewCommentResponseDto(comment.getNo());
    }
}
