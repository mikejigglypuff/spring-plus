package org.example.expert.domain.common.util;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public class FileUtil {
    public static String getRandomFileName(MultipartFile file) {
        return UUID.randomUUID() + "." + getFileExtension(file.getOriginalFilename());
    }

    public static String getFileExtension(String fileName) {
        if (fileName != null) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        return "";
    }
}
