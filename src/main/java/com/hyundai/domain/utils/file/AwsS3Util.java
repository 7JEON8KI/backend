package com.hyundai.domain.utils.file;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.hyundai.global.config.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
/**
 * AWS S3 파일 업로드 유틸
 * author : 강은구
 * fileName : AwsS3Util
 * since : 2/11/24
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AwsS3Util {

    private final AmazonS3 amazonS3;
    private final ApplicationProperties applicationProperties;

    public String saveFile(MultipartFile multipartFile, String savedFileName) throws IOException {
        String filepath = "/" + savedFileName;
        log.debug(filepath);
        String bucket = applicationProperties.getAWS_BUCKET();

        long size = multipartFile.getSize();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());
        metadata.setContentLength(size);

        amazonS3.putObject(bucket, filepath, multipartFile.getInputStream(), metadata);
        return amazonS3.getUrl(bucket, filepath).toString();
    }
    public void deleteFile(String storedFileName) throws AmazonServiceException{
        String bucket = applicationProperties.getAWS_BUCKET();
        amazonS3.deleteObject(new DeleteObjectRequest(bucket + "/", storedFileName));
    }
}
