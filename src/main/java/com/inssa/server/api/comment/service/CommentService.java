package com.inssa.server.api.comment.service;

import com.inssa.server.api.comment.dao.CommentDao;
import com.inssa.server.api.comment.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("CommentService")
@RequiredArgsConstructor
public class CommentService {

    private final CommentDao commentdao;

    public List<CommentDto> selectList(int boardNo){
        return commentdao.selectList(boardNo);
    }

}
