package com.inssa.server.api.board.comment.service;

import com.inssa.server.api.board.comment.dto.*;
import com.inssa.server.api.board.comment.model.Comment;
import com.inssa.server.api.board.commentlike.data.CommentLikeRepository;
import com.inssa.server.api.board.commentlike.dto.CommentLikeResponseDto;
import com.inssa.server.api.board.comment.dto.CommentListResponseDto;
import com.inssa.server.api.board.post.data.PostRepository;
import com.inssa.server.api.image.dto.ImageResponseDto;
import com.inssa.server.api.image.model.Image;
import com.inssa.server.api.board.comment.data.CommentRepository;
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
                new CommentLikeResponseDto(comment.getNo(), hasLiked, comment.getLikeCount())
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
                new CommentLikeResponseDto(comment.getNo(), hasLiked, comment.getLikeCount()),
                comment.getChildren().stream().map(child -> getCommentResponse(child, currentUserNo)).toList()
        );
    }

    private void validatePostByNo(Long postNo) {
        if (!postRepository.existsByStatusAndNo(BoardStatus.VISIBLE, postNo)) {
            throw new InssaException(ErrorCode.NOT_FOUND, "게시글을 찾을 수 없습니다.");
        }
    }

    private void validateCommentByPostNoAndParentCommentNo(Long postNo, Long parentNo) {
        validatePostByNo(postNo);
        // 상위 댓글이 삭제되지 않고, 최상위 댓글인 경우에만 하위 댓글 작성 가능
        Long parentPostNo = commentRepository.findPostNoByStatusAndNoAndParentNull(BoardStatus.VISIBLE, parentNo)
                .orElseThrow(() -> new InssaException(ErrorCode.INVALID, "상위 댓글이 올바르지 않습니다."))
                .getPostNo();
        // 상위 댓글과 추가할 댓글의 게시글 일치 여부 검사
        if (!Objects.equals(postNo, parentPostNo)) {
            throw new InssaException(ErrorCode.INVALID, "게시글 정보가 올바르지 않습니다.");
        }
    }

    private void validateAuthority(Long requestUserNo, Long commentWriterNo) {
        log.debug("requestUserNo = {}", requestUserNo);
        log.debug("commentWriterNo = {}", commentWriterNo);
        if (!Objects.equals(requestUserNo, commentWriterNo)) {
            throw new InssaException(ErrorCode.FORBIDDEN, "댓글 작성자가 아닙니다.");
        }
    }

    private Comment findAndValidateCommentByNo(Long commentNo) {
        return commentRepository.findByStatusAndNo(BoardStatus.VISIBLE, commentNo)
                .orElseThrow(() -> new InssaException(ErrorCode.NOT_FOUND, "댓글을 찾을 수 없습니다."));
    }

    /**
     * 댓글 등록 API
     * @param request: 댓글 요청 정보
     * @return 생성된 댓글 PK
     */
    @Transactional
    public CommentNoResponseDto createComment(CommentRequestDto request) {
        if (Objects.nonNull(request.getParentNo())){
            validateCommentByPostNoAndParentCommentNo(request.getPostNo(), request.getParentNo());
        }
        Comment comment = commentRepository.save(
                Comment.builder()
                        .userNo(request.getUserNo())
                        .postNo(request.getPostNo())
                        .parentCommentNo(request.getParentNo())
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
        validatePostByNo(postNo);
        Page<Comment> comments = commentRepository.findByParentNoNullAndStatusAndPostNo(BoardStatus.VISIBLE, postNo, pageable);
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
        Comment comment = findAndValidateCommentByNo(request.getCommentNo());
        validateAuthority(request.getUserNo(), comment.getUserNo());
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
        Comment comment = findAndValidateCommentByNo(request.getCommentNo());
        validateAuthority(request.getUserNo(), comment.getUserNo());
        comment.delete();
        return new CommentNoResponseDto(comment.getNo());
    }
}
