package com.inssa.server.common.file;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class FileUploader {
    public String upload(Resource resource) {
        String filename = resource.getFilename();
        String name = filename.split(".")[0];
        String extension = filename.substring(filename.lastIndexOf(".") + 1);
        File tempFile = null;
        try {
            tempFile = File.createTempFile(name, "." + extension);

            InputStream input = resource.getInputStream();
            OutputStream output = new FileOutputStream(tempFile);
            input.transferTo(output);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return tempFile.getAbsolutePath();
    }

}
