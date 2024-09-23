package com.developer.image.command.service;


import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.developer.image.command.entity.Image;
import com.developer.image.command.repository.ImageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.UUID;

@Slf4j
@Service
public class ImageService {

    private final AmazonS3Client amazonS3Client;
    private final String bucket;
    private final ImageRepository imageRepository;

    // S3Config 에서 만든 amazonS3 client 의존성 주입
    public ImageService(AmazonS3Client amazonS3Client, @Value("${cloud.aws.s3.bucket}") String bucket, ImageRepository imageRepository) {
        this.amazonS3Client = amazonS3Client;
        this.bucket = bucket;
        this.imageRepository = imageRepository;
    }

    public void upload(MultipartFile multipartFiles[], String dirName) throws IOException {

        Long teamPostCode = 1L;
        String originalFileName;
        String uuid;
        String uniqueFileName;
        String fileName;
        String fileType;
        String fileSize;
        Image[] images = new Image[multipartFiles.length];
        String[] uploadImageUrls = new String[multipartFiles.length];

        for (int i = 0; i < multipartFiles.length; i++) {

            // 파일 이름에서 공백을 제거한 새로운 파일 이름 생성 >> origin_file_name
            originalFileName = multipartFiles[i].getOriginalFilename();

            // UUID를 파일명에 추가
            uuid = UUID.randomUUID().toString();

            uniqueFileName = uuid + "_" + originalFileName.replaceAll("\\s", "_");

            // 저장될 directory 명 + 파일명 >> filName
            fileName = dirName + "/" + uniqueFileName;
            log.info("fileName: " + fileName);

            fileType = multipartFiles[i].getContentType();

            fileSize = String.valueOf(multipartFiles[i].getSize()/1000)+"byte";

            images[i] = new Image();

            images[i].uploadTeamPostImage(
                    originalFileName,
                    fileName,
                    fileType,
                    fileSize,
                    teamPostCode
            );
            imageRepository.save(images[i]);

            File uploadFile = convert(multipartFiles[i],uniqueFileName);

            String uploadImageUrl = putS3(uploadFile, fileName);

            removeNewFile(uploadFile);

            uploadImageUrls[i] = uploadImageUrl;
        }

    }

    private File convert(MultipartFile file, String uniqueFileName) throws IOException {

        File convertFile = new File(uniqueFileName);
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            } catch (IOException e) {
                log.error("파일 변환 중 오류 발생: {}", e.getMessage());
                throw e;
            }
            return convertFile;
        }
        throw new IllegalArgumentException(String.format("파일 변환에 실패했습니다."));
    }

    private String putS3(File uploadFile, String fileName) {

        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        log.info(uploadFile + "업로드");
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

    public void deleteFile(String fileName) {
        try {

            // URL 디코딩을 통해 원래의 파일 이름을 가져옵니다.
            String decodedFileName = URLDecoder.decode(fileName, "UTF-8");
            log.info("Deleting file from S3: " + decodedFileName);
            amazonS3Client.deleteObject(bucket, decodedFileName);


        } catch (UnsupportedEncodingException e) {
            log.error("Error while decoding the file name: {}", e.getMessage());
        }
    }

//    public String updateFile(MultipartFile newFile, String oldFileName, String dirName) throws IOException {
//        // 기존 파일 삭제
//        log.info("S3 oldFileName: " + oldFileName);
//        deleteFile(oldFileName);
//        // 새 파일 업로드
//        return upload(newFile, dirName);
//    }
}
