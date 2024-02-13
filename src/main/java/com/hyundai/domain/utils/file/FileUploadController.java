package com.hyundai.domain.utils.file;

import com.hyundai.global.config.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class FileUploadController {

    @Autowired
    private AwsS3Util s3Service;
    @Autowired
    private ApplicationProperties applicationProperties;

    // 파일 업로드 처리
    @PostMapping("upload")
    public ResponseEntity<Object> upload(MultipartFile[] multipartFileList) throws Exception {
        System.out.println(applicationProperties.getAWS_ACCESS_KEY());
        List<String> imagePathList = new ArrayList<>();
        System.out.println(multipartFileList);
        for(MultipartFile multipartFile: multipartFileList) {

            String originalFileName = multipartFile.getOriginalFilename();
            System.out.println(originalFileName);
            // ========= 파일명 중복 방지 처리 ========= //
            String uuidFileName = getUuidFileName(originalFileName);

            // ========= 서버에 파일 저장 ========= //
            String res = s3Service.saveFile(multipartFile, originalFileName);
            System.out.println(res);
            imagePathList.add(res);
        }
        return new ResponseEntity<Object>(imagePathList, HttpStatus.OK);
    }

    private static String getUuidFileName(String originalFileName) {
        return UUID.randomUUID().toString() + "_" + originalFileName;
    }
}
