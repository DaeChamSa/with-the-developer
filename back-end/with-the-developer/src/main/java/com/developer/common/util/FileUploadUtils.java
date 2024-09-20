package com.developer.common.util;

import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileUploadUtils {

    public static List<String> saveFile(String uploadDir, MultipartFile[] multipartFile) {

        String replaceFileName = "";
        List<String> replaceFileNameList = new ArrayList<>();

        try {
            for(MultipartFile file : multipartFile) {
                InputStream inputStream = file.getInputStream();
                Path uploadPath = Paths.get(uploadDir);

                /* 업로드 경로가 존재하지 않을 시 경로 먼저 생성 */
                if(!Files.exists(uploadPath))
                    Files.createDirectories(uploadPath);

                /* 파일명 생성 */
                replaceFileName = UUID.randomUUID() + "." + FilenameUtils.getExtension(file.getOriginalFilename());

                /* 파일명 전달하기 위해 리스트에 저장 */

                replaceFileNameList.add(replaceFileName);

                /* 파일 저장 */
                Path filePath = uploadPath.resolve(replaceFileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

            }


        } catch (IOException e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_IO_ERROR);
        }

        return replaceFileNameList;
    }
}

