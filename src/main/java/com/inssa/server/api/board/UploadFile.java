package com.inssa.server.api.board;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

public class UploadFile {

    @Value("${spring.resource}") private String path;


    // path로 설정한 경로 아래에 현재시각을 이름으로 폴더를 생성함
    public String makeDir() {
        Calendar calendar = Calendar.getInstance();

        String year = File.separator + calendar.get(Calendar.YEAR) + "";
        String month = year + File.separator + new DecimalFormat("00").format(calendar.get(Calendar.MONTH) + 1);
        String date = month + File.separator + new DecimalFormat("00").format(calendar.get(Calendar.DATE));

        if(!new File(path + date, date).exists()) {
            new File(path, year).mkdir();
            new File(path, month).mkdir();
            new File(path, date).mkdir();
        }

        return date;
    }

    // makeDir로 생성한 폴더 아래에 uuid로 랜덤한 문자 생성 후 파일을 만들고 DB에 '파일 경로 + 이름'을 반환
    public String fileUpload(MultipartFile files) throws IOException {

        UUID uuid = UUID.randomUUID();
        String fileName = File.separator + uuid + "_" + files.getOriginalFilename();
        String dir = makeDir();

        File uploadFile = new File(path + dir, fileName);

        uploadFile.createNewFile();
        FileCopyUtils.copy(files.getBytes(), uploadFile);

        return File.separator + "upload" + dir + fileName;

    }
}
