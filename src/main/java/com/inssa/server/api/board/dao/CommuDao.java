package com.inssa.server.api.board.dao;

import com.inssa.server.api.board.dto.BoardDto;
import com.inssa.server.api.board.mapper.CommuMapper;
import com.inssa.server.common.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository @RequiredArgsConstructor
public class CommuDao {
   private final CommuMapper commuMapper;
   public int insert(BoardDto board) {
      int result = commuMapper.insertBoard(board);
      return result;
   }

   public List<BoardDto> selectBoardList(){
      List<BoardDto> result = commuMapper.selectBoardList();
      return result;
   }

   public List<BoardDto> selectBoard(int boardNo){
      List<BoardDto> result = commuMapper.selectBoard(boardNo);
      return result;
   }

   public List<BoardDto> deleteBoard(int boardNo){
      List<BoardDto> result = commuMapper.deleteBoard(boardNo);
      return result;
   }

   public List<BoardDto> updateBoard(int boardNo){
      List<BoardDto> result = commuMapper.updateBoard(boardNo);
      return result;
   }

   public List<BoardDto> searchBoardList(BoardDto dto){
      List<BoardDto> result = commuMapper.searchBoardList(dto);
      return result;
   }

   public Pagination searchListCount(BoardDto dto) {
      Pagination page = commuMapper.searchListCount(dto);
      return page;
   }

   @Transactional public int updateView(int boardNo) {
      int result  = commuMapper.updateView(boardNo);
      return result;
   }

   @Transactional public List<BoardDto> updateLike(BoardDto dto) {
      List<BoardDto> resultList = commuMapper.updateLike(dto);
      return resultList;
   }

   @Transactional public List<BoardDto> updateZzim(BoardDto dto) {
      List<BoardDto> resultList = commuMapper.updateZzim(dto);
      return resultList;
   }

}
