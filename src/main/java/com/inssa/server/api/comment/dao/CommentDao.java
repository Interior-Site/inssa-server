package com.inssa.server.api.comment.dao;

import com.inssa.server.api.comment.dto.CommentDto;
import com.inssa.server.api.comment.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentDao {

    private final CommentMapper commentMapper;

    public List<CommentDto> selectList(int boardNo){
        return commentMapper.selectList(boardNo);
    }

    public int insertComment(CommentDto comment) {
        return commentMapper.insertComment(comment);
    }

    public int updateComment(CommentDto comment) {
        return commentMapper.updateComment(comment);
    }


    public int deleteComment(CommentDto comment) {
        return commentMapper.deleteComment(comment);
    }

    public int insertReComment(CommentDto comment) {
        return commentMapper.insertReComment(comment);
    }
}
