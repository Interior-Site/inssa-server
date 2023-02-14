package com.inssa.server.api.board.dao;

import com.inssa.server.api.board.dto.BoardDto;
import com.inssa.server.api.board.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository @RequiredArgsConstructor
public class BoardDao {
   private final BoardMapper boardMapper;
   public BoardDto insert(BoardDto board) {
      return boardMapper.insertBoard(board);
   }
}
