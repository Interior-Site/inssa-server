package com.inssa.server.api.review.like.service;

import com.inssa.server.api.review.build.data.BuildReviewRepository;
import com.inssa.server.api.review.build.model.BuildReview;
import com.inssa.server.api.review.like.data.BuildReviewLikeRepository;
import com.inssa.server.api.review.like.data.OrderReviewLikeRepository;
import com.inssa.server.api.review.like.dto.ReviewLikeCreateResponseDto;
import com.inssa.server.api.review.like.dto.ReviewLikeDeleteResponseDto;
import com.inssa.server.api.review.like.model.BuildReviewLike;
import com.inssa.server.api.review.like.model.OrderReviewLike;
import com.inssa.server.api.review.order.data.OrderReviewRepository;
import com.inssa.server.api.review.order.model.OrderReview;
import com.inssa.server.api.user.data.UserRepository;
import com.inssa.server.api.user.model.User;
import com.inssa.server.common.code.ErrorCode;
import com.inssa.server.common.exception.InssaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReviewLikeService {
    private final OrderReviewRepository orderReviewRepository;
    private final BuildReviewRepository buildReviewRepository;
    private final OrderReviewLikeRepository orderReviewLikeRepository;
    private final BuildReviewLikeRepository buildReviewLikeRepository;
    private final UserRepository userRepository;


    private User findUserById(Long userNo) {
        return userRepository.findById(userNo)
                .orElseThrow(() -> new InssaException(ErrorCode.NOT_FOUND, "사용자가 존재하지 않습니다"));
    }

    private OrderReview findOrderReviewById(Long orderReviewNo) {
        OrderReview review = orderReviewRepository.findById(orderReviewNo)
                .orElseThrow(() -> new InssaException(ErrorCode.NOT_FOUND, "견적 후기가 존재하지 않습니다."));
        if (!review.getStatus().isVisible()) {
            throw new InssaException(ErrorCode.NOT_FOUND, "견적 후기가 존재하지 않습니다.");
        }
        return review;
    }

    private BuildReview findBuildReviewById(Long buildReviewNo) {
        BuildReview review = buildReviewRepository.findById(buildReviewNo)
                .orElseThrow(() -> new InssaException(ErrorCode.NOT_FOUND, "시공 후기가 존재하지 않습니다."));
        if (!review.getStatus().isVisible()) {
            throw new InssaException(ErrorCode.NOT_FOUND, "시공 후기가 존재하지 않습니다.");
        }
        return review;
    }

    private OrderReviewLike findOrderReviewLikeByUserNoAndOrderReviewNo(Long userNo, Long orderReviewNo) {
        return orderReviewLikeRepository.findByUserNoAndOrderReviewNo(userNo, orderReviewNo)
                .orElseThrow(() -> new InssaException(ErrorCode.NOT_FOUND, "공감하지 않은 후기는 취소할 수 없습니다."));
    }

    private BuildReviewLike findBuildReviewLikeByUserNoAndOrderReviewNo(Long userNo, Long buildReviewNo) {
        return buildReviewLikeRepository.findByUserNoAndBuildReviewNo(userNo, buildReviewNo)
                .orElseThrow(() -> new InssaException(ErrorCode.NOT_FOUND, "공감하지 않은 후기는 취소할 수 없습니다."));
    }

    private User findUserAndValidateAuthority(Long userNo) {
        User user = findUserById(userNo);
        if (!user.isActive()) {
            throw new InssaException(ErrorCode.FORBIDDEN, "공감 권한이 없습니다.");
        }
        return user;
    }

    private void validateDuplicateOrderReviewLike(Long userNo, Long orderReviewNo) {
        if (orderReviewLikeRepository.existsByUserNoAndOrderReviewNo(userNo, orderReviewNo)) {
            throw new InssaException(ErrorCode.CONFLICT, "이미 공감한 후기입니다.");
        }
    }

    private void validateDuplicateBuildReviewLike(Long userNo, Long buildReviewNo) {
        if (buildReviewLikeRepository.existsByUserNoAndBuildReviewNo(userNo, buildReviewNo)) {
            throw new InssaException(ErrorCode.CONFLICT, "이미 공감한 후기입니다.");
        }
    }

    /**
     * 견적 후기 공감
     * @param orderReviewNo 견적 후기 PK
     * @param userNo 사용자 PK
     * @return 공감한 정보
     */
    @Transactional
    public ReviewLikeCreateResponseDto createOrderReviewLike(Long orderReviewNo, Long userNo) {
        User user = findUserAndValidateAuthority(userNo);
        OrderReview orderReview = findOrderReviewById(orderReviewNo);
        validateDuplicateOrderReviewLike(userNo, orderReviewNo);

        OrderReviewLike like = OrderReviewLike.builder()
                .orderReview(orderReview).user(user).build();
        return new ReviewLikeCreateResponseDto(userNo, orderReviewNo,
                orderReviewLikeRepository.save(like).getNo(), true);
    }

    /**
     * 견적 후기 공감 취소
     * @param orderReviewNo 견적 후기 PK
     * @param userNo 사용자 PK
     * @return 견적 후기 PK, 공감 여부(false)
     */
    @Transactional
    public ReviewLikeDeleteResponseDto deleteOrderReviewLike(Long orderReviewNo, Long userNo) {
        findUserAndValidateAuthority(userNo);
        findOrderReviewById(orderReviewNo);
        OrderReviewLike like = findOrderReviewLikeByUserNoAndOrderReviewNo(userNo, orderReviewNo);
        orderReviewLikeRepository.delete(like);
        return new ReviewLikeDeleteResponseDto(userNo, orderReviewNo, false);
    }

    /**
     * 시공 후기 공감
     * @param buildReviewNo 시공 후기 PK
     * @param userNo 사용자 PK
     * @return 공감한 정보
     */
    @Transactional
    public ReviewLikeCreateResponseDto createBuildReviewLike(Long buildReviewNo, Long userNo) {
        User user = findUserAndValidateAuthority(userNo);
        BuildReview buildReview = findBuildReviewById(buildReviewNo);
        validateDuplicateBuildReviewLike(userNo, buildReviewNo);

        BuildReviewLike like = BuildReviewLike.builder()
                .user(user).buildReview(buildReview).build();
        return new ReviewLikeCreateResponseDto(userNo, buildReviewNo,
                buildReviewLikeRepository.save(like).getNo(), true);
    }

    /**
     * 시공 후기 공감 취소
     * @param buildReviewNo 시공 후기 PK
     * @param userNo 사용자 PK
     * @return 시공후기 PK, 공감 여부(false)
     */
    @Transactional
    public ReviewLikeDeleteResponseDto deleteBuildReviewLike(Long buildReviewNo, Long userNo) {
        findUserAndValidateAuthority(userNo);
        findBuildReviewById(buildReviewNo);
        BuildReviewLike like = findBuildReviewLikeByUserNoAndOrderReviewNo(userNo, buildReviewNo);
        buildReviewLikeRepository.delete(like);
        return new ReviewLikeDeleteResponseDto(userNo, buildReviewNo,false);
    }
}
