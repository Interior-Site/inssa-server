package com.inssa.server.api.board.dao;

import com.inssa.server.api.board.dto.BoardDto;
import com.inssa.server.api.board.dto.LikeDto;
import com.inssa.server.api.board.mapper.AskMapper;
import com.inssa.server.common.Files;
import com.inssa.server.common.Pagination;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.RowBounds;
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

    public List<BoardDto> searchBoardList(BoardDto dto, Pagination page){
        int offset = (page.getCurrentPage() - 1) * page.getLimit();
        RowBounds rowBounds = new RowBounds(offset, page.getLimit());
        List<BoardDto> result = mapper.searchBoardList(dto, rowBounds);
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

    @Transactional public List<BoardDto> updateLike(BoardDto dto) {
        List<BoardDto> resultList = mapper.updateLike(dto);
        return resultList;
    }

    @Transactional public List<BoardDto> updateZzim(BoardDto dto) {
        List<BoardDto> resultList = mapper.updateZzim(dto);
        return resultList;
    }

    public LikeDto likeCheck(int likeNo) {
        LikeDto like = mapper.likeCheck(likeNo);
        return like;
    }

    public int insertImg(List<Files> files) {
        int result = mapper.insertImg(files);
        return result;
    }
}
