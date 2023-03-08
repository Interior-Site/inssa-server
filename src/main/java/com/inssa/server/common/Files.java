package com.inssa.server.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data @AllArgsConstructor
public class Files {

    // 사용자가 업로드 한 파일명
    private String uploadName;

    // 서버에서 관리하는 파일명
    private String storeName;

    private String filePath;

    private int boardNo;

    private int fileNo;

    private Date createdDate;

    private int fileLevel;

    public Files(String uploadName, String storeName) {
        this.uploadName = uploadName;
        this.storeName = storeName;
    }
    public Files() {    }
}
