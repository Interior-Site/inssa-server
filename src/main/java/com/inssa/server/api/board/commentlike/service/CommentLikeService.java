package com.inssa.server.api.board.commentlike.service;

import com.inssa.server.api.board.comment.data.CommentRepository;
import com.inssa.server.api.board.comment.model.Comment;
import com.inssa.server.api.board.commentlike.data.CommentLikeRepository;
import com.inssa.server.api.board.commentlike.dto.CommentLikeResponseDto;
import com.inssa.server.api.board.commentlike.model.CommentLike;
import com.inssa.server.common.code.ErrorCode;
import com.inssa.server.common.exception.InssaException;
import com.inssa.server.share.board.BoardStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;

    private boolean alreadyLikedComment(Long userNo, Long commentNo) {
        return commentLikeRepository.existsByUserNoAndCommentNo(userNo, commentNo);
    }

    private long countCommentLikeByNo(Long commentNo) {
        return commentLikeRepository.countByCommentNo(commentNo);
    }

    @Transactional
    public CommentLikeResponseDto createLike(Long commentNo, Long userNo) {
        Comment comment = commentRepository.findByStatusAndNo(BoardStatus.VISIBLE, commentNo)
                .orElseThrow(() -> new InssaException(ErrorCode.NOT_FOUND, "댓글이 존재하지 않습니다."));
        if (alreadyLikedComment(userNo, commentNo)){
            throw new InssaException(ErrorCode.CONFLICT, "이미 공감한 댓글입니다.");
        }
        CommentLike commentLike = commentLikeRepository.save(CommentLike.builder()
                .commentNo(commentNo)
                .userNo(userNo)
                .build());
        comment.addCommentLike(commentLike);
        return new CommentLikeResponseDto(commentNo, true, comment.getLikeCount());
    }

    @Transactional
    public CommentLikeResponseDto deleteLike(Long commentNo, Long userNo) {
        CommentLike commentLike = commentLikeRepository.findByUserNoAndCommentNo(userNo, commentNo)
                .orElseThrow(() -> new InssaException(ErrorCode.INTERNAL_SERVER_ERROR, "공감하지 않은 댓글은 취소할 수 없습니다."));
        commentLikeRepository.delete(commentLike);
        long likeCount = countCommentLikeByNo(commentNo);
        return new CommentLikeResponseDto(commentNo, false, likeCount);
    }
}
