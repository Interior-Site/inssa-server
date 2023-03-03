package com.inssa.server.common;


import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {

    // 루트 경로 가져오기
    private final String rootPath = System.getProperty("user.dir");

    // 프로젝트 루트 경로에 있는 files 디렉토리
    private final String fildDir = rootPath + "/files/";

    public String getFullPath(String fileName) {
        return fildDir + fileName;
    }

    public Files storeFile(MultipartFile multipartFile) throws IOException {

        if(multipartFile.isEmpty()) {
            return null;
        }

        String originName = multipartFile.getOriginalFilename();

        // 서버에서 관리하는 파일명 : UUID + .확장자명(업로드한 파일명)
        String storeName = UUID.randomUUID() + "." + extractExt(originName);

        // 파일경로 + storeName에 저장
        multipartFile.transferTo(new File(getFullPath(storeName)));

        return new Files(originName, storeName);
    }

    // 다중파일 첨부되었을 때 처리부
    public List<Files> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<Files> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if(!multipartFile.isEmpty()) {
                storeFileResult.add(storeFile(multipartFile));
            }
        }
        return storeFileResult;
    }

    // 확장자 추출
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
