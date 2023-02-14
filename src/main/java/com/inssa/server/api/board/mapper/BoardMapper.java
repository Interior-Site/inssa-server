package com.inssa.server.api.board.mapper;

import com.inssa.server.api.board.dto.BoardDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {
    BoardDto insertBoard(BoardDto board);
}
