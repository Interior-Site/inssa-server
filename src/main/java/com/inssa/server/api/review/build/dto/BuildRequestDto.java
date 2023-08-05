package com.inssa.server.api.review.build.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


import java.util.Date;

@Data
@AllArgsConstructor
public class BuildRequestDto {

    private Long buildNo;         // 시공후기 글번호
    private Long buildType;        // 건물유형
    private Long categoryNo;      // 카테고리 번호
    private String title;        // 글제목
    private String content;      // 글내용
    private Date createdDate;    // 작성일
    private Date modifiedDate;   // 수정일
    private String status;       // 글상태
    private int viewCount;       // 조회수
    private Long userNo;          // 작성자No

    @Builder(builderMethodName = "createBuilder", builderClassName = "createBuilder")
    public BuildRequestDto(Long buildType, Long categoryNo, String title, String content, Long userNo){
        this.buildType = buildType;
        this.categoryNo = categoryNo;
        this.title = title;
        this.content = content;
        this.userNo = userNo;
    }

    @Builder(builderMethodName = "updateBuilder", builderClassName = "updateBuilder")
    public BuildRequestDto(Long buildNo, String title, String content, Long userNo){
        this.buildNo = buildNo;
        this.title = title;
        this.content = content;
        this.userNo = userNo;
    }

    @Builder(builderMethodName = "deleteBuilder", builderClassName = "deleteBuilder")
    public BuildRequestDto(Long buildNo,Long userNo){
        this.buildNo = buildNo;
        this.userNo = userNo;
    }
}
