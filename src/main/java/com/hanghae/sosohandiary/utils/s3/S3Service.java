package com.hanghae.sosohandiary.utils.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.hanghae.sosohandiary.domain.diary.dto.DiaryRequestDto;
import com.hanghae.sosohandiary.domain.diary.entity.Diary;
import com.hanghae.sosohandiary.domain.diarydetil.dto.DiaryDetailRequestDto;
import com.hanghae.sosohandiary.domain.diarydetil.entity.DiaryDetail;
import com.hanghae.sosohandiary.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
                File uploadFile = convert(multipartFile).orElseThrow(
                        () -> new IllegalArgumentException("파일 전환 실패")
                );
                Diary.of(diaryRequestDto, upload(uploadFile), member);
            }
        }
    }

    public void uploadDiaryDetail(List<MultipartFile> multipartFilelist, DiaryDetailRequestDto diaryDetailRequestDto, Member member) throws IOException {

        for (MultipartFile multipartFile : multipartFilelist) {
            if (multipartFile != null) {
                File uploadFile = convert(multipartFile).orElseThrow(
                        () -> new IllegalArgumentException("파일 전환 실패")
                );
                DiaryDetail.of(diaryDetailRequestDto, upload(uploadFile), member);
            }
        }
    }

    // S3에 파일이름 저장 후 업로드
    private String upload(File uploadFile) {
        String fileName = "" + UUID.randomUUID();
        uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    private String putS3(File uploadFile, String fileName) {
        S3Client.putObject(new PutObjectRequest(bucketName, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
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
        File convertFile = new File(multipartFile.getOriginalFilename());
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(multipartFile.getBytes());
            }
            return Optional.of(convertFile);
        }

        return Optional.empty();
    }

    public String getThumbnailPath(String path) {
        return S3Client.getUrl(bucketName, path).toString();
    }

    public String getUploadImageUrl() {
        return uploadImageUrl;
    }

    public void deleteFile(String fileName) {
        DeleteObjectRequest request = new DeleteObjectRequest(bucketName, fileName);
        S3Client.deleteObject(request);
    }

}