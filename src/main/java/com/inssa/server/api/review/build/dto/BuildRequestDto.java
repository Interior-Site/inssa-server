package com.inssa.server.api.review.build.dto;

import lombok.Builder;
import lombok.Data;


import java.util.Date;

@Data
public class BuildRequestDto {

    private Long buildNo;         // 시공후기 글번호
    private String buildType;    // 건물유형
    private int categoryNo;      // 카테고리 번호
    private String title;        // 글제목
    private String content;      // 글내용
    private Date createdDate;    // 작성일
    private Date modifiedDate;   // 수정일
    private String status;       // 글상태
    private int viewCount;       // 조회수
    private Long userNo;          // 작성자No

    @Builder(builderMethodName = "createBuilder")
    public BuildRequestDto(String buildType, int categoryNo, String title, String content, Long userNo){
        this.buildType = buildType;
        this.categoryNo = categoryNo;
        this.title = title;
        this.content = content;
        this.userNo = userNo;
    }

    @Builder(builderMethodName = "updateBuilder")
    public BuildRequestDto(String title, String content, Long userNo){
        this.title = title;
        this.content = content;
        this.userNo = userNo;
    }

    @Builder(builderMethodName = "deleteBuilder")
    public BuildRequestDto(Long buildNo,Long userNo){
        this.buildNo = buildNo;
        this.userNo = userNo;
    }
}
