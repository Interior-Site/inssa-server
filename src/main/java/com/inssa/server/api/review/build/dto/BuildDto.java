package com.inssa.server.api.review.build.dto;

import lombok.Data;


import java.util.Date;

@Data
public class BuildDto {

    private int buildNo;         // 시공후기 글번호
    private String buildType;    // 건물유형
    private int categoryNo;      // 카테고리 번호
    private String title;        // 글제목
    private String content;      // 글내용
    private Date createdDate;    // 작성일
    private Date modifiedDate;   // 수정일
    private String status;       // 글상태
    private int viewCount;       // 조회수
    private int userNo;          // 작성자No
}
