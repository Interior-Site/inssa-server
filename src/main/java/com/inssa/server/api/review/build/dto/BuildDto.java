package com.inssa.server.api.review.build.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class BuildDto {

    public int buildNo;         // 시공후기 글번호
    public String buildType;    // 건물유형
    public int categoryNo;      // 카테고리 번호
    public String title;        // 글제목
    public String content;      // 글내용
    public Date createdDate;    // 작성일
    public Date modifiedDate;   // 수정일
    public String status;       // 글상태
    public int viewCount;       // 조회수
    public int userId;          // 작성자ID
}
