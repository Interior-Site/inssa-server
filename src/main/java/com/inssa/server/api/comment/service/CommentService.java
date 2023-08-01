package com.inssa.server.api.comment.service;

import com.inssa.server.api.comment.dao.CommentDao;
import com.inssa.server.api.comment.dto.CommentDto;
import com.inssa.server.common.exception.InssaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("CommentService")
@RequiredArgsConstructor
public class CommentService {

    private final CommentDao commentdao;

    public List<CommentDto> selectList(int boardNo){
        // DAO에서 result에 담긴 댓글목록
        return commentdao.selectList(boardNo);
    }

    public void insertComment(CommentDto comment) {
        int result = 0;
        Integer parentYn = comment.getParentCommentNo();

        // 대댓글인 경우
        if (null != parentYn) {
            result = commentdao.insertReComment(comment);
        } else{ // 댓글인 경우
            result = commentdao.insertComment(comment);
        }

        if(result <= 0) {
            throw new InssaException("댓글 등록 실패");
        }
    }


    public void updateComment(CommentDto comment) {
        int result = commentdao.updateComment(comment);

        if(result <= 0) {
            throw new InssaException("댓글 수정 실패");
        }
    }

    public void deleteComment(CommentDto comment) {
        int result = commentdao.deleteComment(comment);

        if(result <= 0) {
            throw new InssaException("댓글 삭제 실패");
        }
    }
}
