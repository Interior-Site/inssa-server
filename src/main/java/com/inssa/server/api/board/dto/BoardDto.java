package com.inssa.server.api.board.dto;


import com.inssa.server.common.Files;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;


@Getter @Setter
public class BoardDto {

    public int boardNo;         // 게시판 번호
    public int boardTypeNo;    // 게시판 구분 번호 ( 11:후기, 22:문의, 33:소통)
    public int categoryNo;     // 카테고리 번호
    public int parentBoardNo;  // 부모 게시글 번호
    public String title;       // 게시글 제목
    public String content;     // 게시글 내용
    public Date createdDate;   // 게시글 작성일
    public Date modifiedDate;  // 게시글 수정일
    public boolean deleteAt;   // 삭제 여부
    public int viewCount;      // 조회수
    public int likeCount;      // 좋아요 수
    public boolean noticeAt;   // 공지사항 여부

    public String userId;      // 사용자 ID
    public List<MultipartFile> requestImg; // 사용자가 첨부한 이미지
    public List<Files> boardImg; // 사용자가 첨부한 이미지를 저장
    public String url;
    public int likeNo;

    // 검색필터
    public String searchWord; // 검색어
    public String category;// 검색 게시글 카테고리
    public String filter; // 검색 필터 조건




}
