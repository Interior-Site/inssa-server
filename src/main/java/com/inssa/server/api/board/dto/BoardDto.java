package com.inssa.server.api.board.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter @Setter
public class BoardDto {

    public int boardNo;
    public String boardName;
    public String boardGubun;
    public String boardTitle;
    public String boardContent;
    public String boardStatus;
    public Date boardRegDate;
    public Date boardChgDate;
    public int boardView;
    public String boardLike;
    public String boardZzim;
    public String boardNotice;
    public int userNo;

    public String url;

    // 검색필터
    public String searchWord; // 검색어
    public String category;// 검색 게시글 카테고리
    public String filter; // 검색 필터 조건




}
