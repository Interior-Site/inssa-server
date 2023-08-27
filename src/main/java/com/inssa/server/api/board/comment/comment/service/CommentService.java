package com.inssa.server.api.board.comment.comment.service;

import com.inssa.server.api.board.comment.comment.model.Comment;
import com.inssa.server.api.board.comment.like.data.CommentLikeRepository;
import com.inssa.server.api.board.comment.comment.dto.*;
import com.inssa.server.api.board.comment.like.dto.CommentLikeResponseDto;
import com.inssa.server.api.board.comment.comment.dto.CommentListResponseDto;
import com.inssa.server.api.board.post.data.PostRepository;
import com.inssa.server.api.board.post.model.Post;
import com.inssa.server.api.image.dto.ImageResponseDto;
import com.inssa.server.api.image.model.Image;
import com.inssa.server.api.board.comment.comment.data.CommentRepository;
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
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    private CommentUserResponseDto getUserResponse(User user) {
        Image image = user.getProfile();
        ImageResponseDto profile = (Objects.nonNull(image))?
                new ImageResponseDto(image.getId(), image.getImgUrl(), image.getOriginFileName()): null;
        return new CommentUserResponseDto(
                user.getNo(),
                user.getNickname(),
                user.getStatus(),
                profile,
                user.getRole());
    }

    private CommentResponseDto getCommentResponse(Comment comment, Long currentUserNo) {
        boolean hasLiked = commentLikeRepository.existsByUserNoAndCommentNo(currentUserNo, comment.getNo());
        return new CommentResponseDto(
                comment.getNo(),
                comment.getContent(),
                getUserResponse(comment.getUser()),
                comment.getCreatedDate(),
                comment.getModifiedDate(),
                new CommentLikeResponseDto(hasLiked, comment.getLikeCount())
        );
    }

    private CommentListResponseDto getCommentListResponse(Comment comment, Long currentUserNo) {
        boolean hasLiked = commentLikeRepository.existsByUserNoAndCommentNo(currentUserNo, comment.getNo());
        return new CommentListResponseDto(
                comment.getNo(),
                comment.getContent(),
                getUserResponse(comment.getUser()),
                comment.getCreatedDate(),
                comment.getModifiedDate(),
                new CommentLikeResponseDto(hasLiked, comment.getLikeCount()),
                comment.getChildren().stream().map(child -> getCommentResponse(child, currentUserNo)).toList()
        );
    }

    private Post findAndValidatePostByNo(Long postNo) {
        return postRepository.findByStatusAndNo(BoardStatus.VISIBLE, postNo)
                .orElseThrow(() -> new InssaException(ErrorCode.NOT_FOUND, "게시글을 찾을 수 없습니다."));
    }

    private Comment findAndValidateCommentByParentNo(Long parentNo) {
        return commentRepository.findByStatusAndNo(BoardStatus.VISIBLE, parentNo)
                .orElseThrow(() -> new InssaException(ErrorCode.NOT_FOUND, "상위 댓글을 찾을 수 없습니다."));
    }

    private Comment findAndValidateCommentByNo(Long commentNo) {
        return commentRepository.findByStatusAndNo(BoardStatus.VISIBLE, commentNo)
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
     * 댓글 등록 API
     * @param request: 댓글 요청 정보
     * @return 생성된 댓글 PK
     */
    @Transactional
    public CommentNoResponseDto createComment(CommentRequestDto request) {
        User user = findAndValidateUserByNo(request.getUserNo());
        Post post = findAndValidatePostByNo(request.getPostNo());
        Comment parentComment = (Objects.nonNull(request.getParentNo()))? findAndValidateCommentByParentNo(request.getParentNo()): null;
        Comment comment = commentRepository.save(
                Comment.builder()
                        .user(user)
                        .post(post)
                        .parent(parentComment)
                        .content(request.getContent())
                        .build());
        return new CommentNoResponseDto(comment.getNo());
    }

    /**
     * 댓글 목록 API
     * @param postNo: PK
     * @param pageable: page 객체
     * @param userNo: 로그인한 사용자 PK
     * @return 댓글 목록 Page response
     */
    @Transactional(readOnly = true)
    public Page<CommentListResponseDto> findCommentsByNo(Long postNo, Pageable pageable, Long userNo) {
        findAndValidatePostByNo(postNo);
        Page<Comment> comments = commentRepository.findByParentNullAndStatusAndPostNo(BoardStatus.VISIBLE, postNo, pageable);
        List<CommentListResponseDto> result = comments.stream()
                .map(comment -> getCommentListResponse(comment, userNo)).toList();
        return new PageImpl<>(result, comments.getPageable(), comments.getSize());
    }

    /**
     * 댓글 수정 API
     * @param request: 댓글 요청 정보
     * @return 수정된 댓글 PK
     */
    @Transactional
    public CommentNoResponseDto updateComment(CommentRequestDto request) {
        findAndValidatePostByNo(request.getPostNo());
        Comment comment = findAndValidateCommentByNo(request.getCommentNo());
        if (Objects.nonNull(request.getParentNo())) {
            // TODO: 상위 댓글 삭제 여부에 따른 처리
            findAndValidateCommentByParentNo(request.getParentNo());
        }
        validateAuthority(comment.getUser().getNo(), request.getUserNo());
        comment.updateContent(request.getContent());
        return new CommentNoResponseDto(comment.getNo());
    }

    /**
     * 댓글 삭제 API
     * @param request 댓글 삭제 요청 정보
     * @return 삭제된 댓글 PK
     */
    @Transactional
    public CommentNoResponseDto deleteComment(CommentRequestDto request) {
        findAndValidatePostByNo(request.getPostNo());
        Comment comment = findAndValidateCommentByNo(request.getCommentNo());
        validateAuthority(comment.getUser().getNo(), request.getUserNo());
        comment.delete();
        return new CommentNoResponseDto(comment.getNo());
    }
}
