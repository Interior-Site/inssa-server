package com.inssa.server.api.comment.mapper;

import com.inssa.server.api.comment.dto.CommentDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    List<CommentDto> selectList(int commentNo);

    int insertComment(CommentDto comment);
}
