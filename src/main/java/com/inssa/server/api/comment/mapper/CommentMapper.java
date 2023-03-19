package com.inssa.server.api.comment.mapper;

import com.inssa.server.api.comment.dto.CommentDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    List<CommentDto> selectList(int boardNo);

    int insertComment(CommentDto comment);

    int updateComment(CommentDto comment);

    int deleteComment(CommentDto comment);
}
