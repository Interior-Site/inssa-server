package com.inssa.server.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class Files {

    // 사용자가 업로드 한 파일명
    private String uploadName;

    // 서버에서 관리하는 파일명
    private String storeName;
}
