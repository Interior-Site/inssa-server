package com.inssa.server.api.board.dao;

import com.inssa.server.api.board.dto.BoardDto;
import com.inssa.server.api.board.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository @RequiredArgsConstructor
public class BoardDao {
   private final BoardMapper boardMapper;
   public int insert(BoardDto board) {
      int result = boardMapper.insertBoard(board);
      return result;
   }

   public List<BoardDto> selectBoard(int boardNo){
      List<BoardDto> result = boardMapper.selectBoard(boardNo);
      return result;
   }
}
