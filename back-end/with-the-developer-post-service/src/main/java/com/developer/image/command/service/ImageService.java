package com.developer.image.command.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.developer.common.exception.CustomException;
import com.developer.common.exception.ErrorCode;
import com.developer.image.command.dto.ImageUploadDTO;
import com.developer.image.command.entity.Image;
import com.developer.image.command.entity.ImageType;
import com.developer.image.command.repository.ImageRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ImageService {

    private final AmazonS3Client amazonS3Client;
    private final String bucket;
    private final ImageRepository imageRepository;

    // 허용된 파일 확장자 목록
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png");

    // S3Config 에서 만든 amazonS3 client 의존성 주입
    public ImageService(AmazonS3Client amazonS3Client, @Value("${cloud.aws.s3.bucket}") String bucket, ImageRepository imageRepository) {
        this.amazonS3Client = amazonS3Client;
        this.bucket = bucket;
        this.imageRepository = imageRepository;
    }

    // 요청받은 이미지 리스트를 S3와 DB에 저장
    public List<String> upload(MultipartFile[] multipartFiles, ImageType dirName, Long code) throws IOException {

        log.info("upload 시작");
        String originalFileName;
        String uuid;
        String uniqueFileName;
        String fileName;
        String fileType;
        String fileSize;
        List<String> uploadImageUrls = new ArrayList<>();

        for (int i = 0; i < multipartFiles.length; i++) {

            // 파일 이름에서 공백을 제거한 새로운 파일 이름 생성 >> origin_file_name
            originalFileName = multipartFiles[i].getOriginalFilename();

            // 확장자 추출
            fileType = FilenameUtils.getExtension(originalFileName).toLowerCase();

            // 확장자 검증
            if (!ALLOWED_EXTENSIONS.contains(fileType)) {
                throw new CustomException(ErrorCode.NOT_MATCH_FILE_EXTENSION); // 유효하지 않은 확장자 처리
            }

            // UUID를 파일명에 추가
            uuid = UUID.randomUUID().toString();

            uniqueFileName = uuid + "_" + originalFileName.replaceAll("\\s", "_");

            // 저장될 directory 명 + 파일명 >> filName
            fileName = "images/" + dirName + "/" + uniqueFileName;
            log.info("fileName: " + fileName);

            fileSize = String.valueOf(multipartFiles[i].getSize()/1000)+"byte";

            ImageUploadDTO imageUploadDTO = ImageUploadDTO.builder()
                    .originFileName(originalFileName)
                    .fileName(fileName)
                    .fileType(fileType)
                    .fileSize(fileSize)
                    .dirName(dirName)
                    .code(code)
                    .build();

            createImage(imageUploadDTO);

            File uploadFile = convert(multipartFiles[i],uniqueFileName);

            String uploadImageUrl = putS3(uploadFile, fileName);

            removeNewFile(uploadFile);

            uploadImageUrls.add(uploadImageUrl);
        }

        return uploadImageUrls;
    }

    @Transactional
    public void createImage(ImageUploadDTO imageUploadDTO){

        Image image = new Image();

        image.setImageByDir(imageUploadDTO);

        imageRepository.save(image);

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

    @Transactional
    public void updateImage(MultipartFile[] newImages, ImageType dir, Long code) throws IOException {

        List<Image> oldImages = new ArrayList<>();
        switch (dir) {
            case TEAMPOST:
                oldImages = imageRepository.findByTeamPostCode(code);
                break;
            case PROJPOST:
                oldImages = imageRepository.findByProjPostCode(code);
                break;
            case COMU:
                oldImages = imageRepository.findByComuCode(code);
                break;
            case RECRUIT:
                oldImages = imageRepository.findByRecruitCode(code);
                break;
            case GOODS:
                oldImages = imageRepository.findByGoodsCode(code);
                break;
        }

        for (int i = 0; i < oldImages.size(); i++) {
            deleteS3File(oldImages.get(i).getFileName());
            imageRepository.delete(oldImages.get(i));
        }

        if(newImages != null && !newImages[0].isEmpty()){
            upload(newImages,dir,code);
        }

    }

    public void deleteImage(ImageType dir, Long code){

        List<Image> oldImages = new ArrayList<>();

        switch (dir){
            case TEAMPOST : oldImages = imageRepository.findByTeamPostCode(code); break;
            case PROJPOST : oldImages = imageRepository.findByProjPostCode(code); break;
            case COMU : oldImages = imageRepository.findByComuCode(code); break;
            case RECRUIT : oldImages = imageRepository.findByRecruitCode(code); break;
            case GOODS : oldImages = imageRepository.findByGoodsCode(code); break;
        }

        for(int i=0; i<oldImages.size(); i++){
            deleteS3File(oldImages.get(i).getFileName());
            imageRepository.delete(oldImages.get(i));
        }

    }

    public void deleteS3File(String fileName) {

        try {
            // URL 디코딩을 통해 원래의 파일 이름을 가져옵니다.
            String decodedFileName = URLDecoder.decode(fileName, "UTF-8");
            log.info("Deleting file from S3: " + decodedFileName);
            amazonS3Client.deleteObject(bucket, decodedFileName);

        } catch (UnsupportedEncodingException e) {
            log.error("Error while decoding the file name: {}", e.getMessage());
        }
    }

}
