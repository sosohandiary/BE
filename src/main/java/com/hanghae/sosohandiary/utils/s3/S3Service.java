package com.hanghae.sosohandiary.utils.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.hanghae.sosohandiary.domain.diary.dto.DiaryRequestDto;
import com.hanghae.sosohandiary.domain.diary.entity.Diary;
import com.hanghae.sosohandiary.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@Service
@RequiredArgsConstructor
public class S3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    private String uploadImageUrl;
    private final AmazonS3 S3Client;

    public void uploadDiary(List<MultipartFile> multipartFilelist, DiaryRequestDto diaryRequestDto, Member member) throws IOException {

        for (MultipartFile multipartFile : multipartFilelist) {
            if (multipartFile != null) {

                resizeImage(multipartFile);

                File uploadFile = convert(multipartFile).orElseThrow(
                        () -> new IllegalArgumentException("파일 전환 실패")
                );
                Diary.of(diaryRequestDto, upload(uploadFile), member);
            }
        }
    }

    public void uploadProfileImage(List<MultipartFile> multipartFilelist) throws IOException {

        for (MultipartFile multipartFile : multipartFilelist) {

            resizeImage(multipartFile);

            File uploadFile = convert(multipartFile).orElseThrow(
                    () -> new IllegalArgumentException("파일 전환 실패")
            );

            Member.of(uploadImageUrl);
        }

    }

    private String upload(File uploadFile) {

        String fileName = "" + UUID.randomUUID();
        uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    private String putS3(File uploadFile, String fileName) {

        S3Client.putObject(new PutObjectRequest(bucketName, fileName, uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return S3Client.getUrl(bucketName, fileName).toString();
    }


    private void removeNewFile(File targetFile) {

        if (targetFile.delete()) {
            log.info("File delete success");
            return;
        }
        log.info("File delete fail");
    }

    private Optional<File> convert(MultipartFile multipartFile) throws IOException {

        File convertFile = new File(multipartFile.getName());

        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(multipartFile.getBytes());
            }
            return Optional.of(convertFile);
        }

        return Optional.empty();
    }

    public String getUploadImageUrl() {
        return uploadImageUrl;
    }

    public void deleteFile(String fileName) {

        S3Client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
    }

    private File resizeImage(MultipartFile file) throws IOException {

        BufferedImage originalImage = ImageIO.read(file.getInputStream());
        int originWidth = originalImage.getWidth();
        int originHeight = originalImage.getHeight();

        int newWidth = 220;
        int newHeight = 0;
        if (originWidth > newWidth) {
            newHeight = (originHeight * newWidth) / originWidth; // 이미지 비율로 변환
        }

        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        graphics2D.dispose();

        File outputfile = new File(file.getOriginalFilename());
        try {
            if (outputfile.createNewFile()) {

                ByteArrayOutputStream bos = new ByteArrayOutputStream();

                String type = file.getContentType().substring(file.getContentType().indexOf("/")+1);
                ImageIO.write(resizedImage, type, bos);

                InputStream inputStream = new ByteArrayInputStream(bos.toByteArray());

                Files.copy(inputStream, outputfile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                return outputfile;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return null;

    }

}