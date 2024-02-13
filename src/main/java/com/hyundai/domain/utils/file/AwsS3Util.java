package com.hyundai.domain.utils.file;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.hyundai.global.config.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AwsS3Util {

    private final AmazonS3 amazonS3;
    @Autowired
    private ApplicationProperties applicationProperties;

    public String saveFile(MultipartFile multipartFile, String savedFileName) throws IOException {
        String filepath = "/" + savedFileName;
        log.info(filepath);
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
