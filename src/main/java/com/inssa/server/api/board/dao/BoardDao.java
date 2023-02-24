package com.inssa.server.api.board.dao;

import com.inssa.server.api.board.dto.BoardDto;
import com.inssa.server.api.board.mapper.BoardMapper;
import com.inssa.server.common.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository @RequiredArgsConstructor
public class BoardDao {
   private final BoardMapper boardMapper;
   public int insert(BoardDto board) {
      int result = boardMapper.insertBoard(board);
      return result;
   }

   public List<BoardDto> selectBoardList(){
      List<BoardDto> result = boardMapper.selectBoardList();
      return result;
   }

   public List<BoardDto> selectBoard(int boardNo){
      List<BoardDto> result = boardMapper.selectBoard(boardNo);
      return result;
   }

   public List<BoardDto> deleteBoard(int boardNo){
      List<BoardDto> result = boardMapper.deleteBoard(boardNo);
      return result;
   }

   public List<BoardDto> updateBoard(int boardNo){
      List<BoardDto> result = boardMapper.updateBoard(boardNo);
      return result;
   }

   public List<BoardDto> searchBoardList(BoardDto dto){
      List<BoardDto> result = boardMapper.searchBoardList(dto);
      return result;
   }

   public Pagination searchListCount(BoardDto dto) {
      Pagination page = boardMapper.searchListCount(dto);
      return page;
   }

   @Transactional public int updateView(int boardNo) {
      int result  = boardMapper.updateView(boardNo);
      return result;
   }

   @Transactional public List<BoardDto> updateLike(BoardDto dto) {
      List<BoardDto> resultList = boardMapper.updateLike(dto);
      return resultList;
   }
}
