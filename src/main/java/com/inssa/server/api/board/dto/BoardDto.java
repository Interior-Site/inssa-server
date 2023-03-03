package com.inssa.server.api.board.dto;


import com.inssa.server.common.Files;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;


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

    public String userId;
    public List<MultipartFile> requestImg; // 사용자가 첨부한 이미지
    public List<Files> boardImg; // 사용자가 첨부한 이미지를 저장

    public String boardCategory;

    public String url;

    // 검색필터
    public String searchWord; // 검색어
    public String category;// 검색 게시글 카테고리
    public String filter; // 검색 필터 조건




}
