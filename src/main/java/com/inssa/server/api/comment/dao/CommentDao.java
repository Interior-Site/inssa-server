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
        return (List<CommentDto>) commentMapper.selectList(boardNo);
    }

}
