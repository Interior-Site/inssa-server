package com.inssa.server.api.board.dao;

import com.inssa.server.api.board.dto.BoardDto;
import com.inssa.server.api.board.mapper.AskMapper;
import com.inssa.server.common.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AskDao {

    private final AskMapper mapper;

    public int insert(BoardDto board) {
        int result = mapper.insertBoard(board);
        return result;
    }

    public List<BoardDto> selectBoardList(){
        List<BoardDto> result = mapper.selectBoardList();
        return result;
    }

    public List<BoardDto> selectBoard(int boardNo){
        List<BoardDto> result = mapper.selectBoard(boardNo);
        return result;
    }

    public List<BoardDto> deleteBoard(int boardNo){
        List<BoardDto> result = mapper.deleteBoard(boardNo);
        return result;
    }

    public List<BoardDto> updateBoard(int boardNo){
        List<BoardDto> result = mapper.updateBoard(boardNo);
        return result;
    }

    public List<BoardDto> searchBoardList(BoardDto dto){
        List<BoardDto> result = mapper.searchBoardList(dto);
        return result;
    }

    public Pagination searchListCount(BoardDto dto) {
        Pagination page = mapper.searchListCount(dto);
        return page;
    }

    @Transactional
    public int updateView(int boardNo) {
        int result  = mapper.updateView(boardNo);
        return result;
    }
}
