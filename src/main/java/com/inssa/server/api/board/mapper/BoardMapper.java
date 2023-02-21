package com.inssa.server.api.board.mapper;

import com.inssa.server.api.board.dto.BoardDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    int insertBoard(BoardDto board);

    List<BoardDto> selectBoardList();

    List<BoardDto> selectBoard(int boardNo);

    List<BoardDto> deleteBoard(int boardNo);

    List<BoardDto> updateBoard(int boardNo);
}
