package com.inssa.server.api.board.mapper;

import com.inssa.server.api.board.dto.BoardDto;
import com.inssa.server.api.board.dto.LikeDto;
import com.inssa.server.common.Pagination;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommuMapper {
    int insertBoard(BoardDto board);

    List<BoardDto> selectBoardList();

    List<BoardDto> selectBoard(int boardNo);

    List<BoardDto> deleteBoard(int boardNo);

    List<BoardDto> updateBoard(int boardNo);

    List<BoardDto> searchBoardList(BoardDto dto);

    Pagination searchListCount(BoardDto dto);

    int updateView(int boardNo);

    List<BoardDto> updateLike(BoardDto dto);

    List<BoardDto> updateZzim(BoardDto dto);

    LikeDto likeCheck(int likeNo);
}
