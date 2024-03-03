package com.hyundai.domain.utils.file;

import com.hyundai.global.config.ApplicationProperties;
import com.hyundai.global.message.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;
/**
 * AWS S3 파일 업로드 테스트 컨트롤러
 * author : 강은구
 * fileName : AwsS3Util
 * since : 2/11/24
 */
@RestController
@RequiredArgsConstructor
public class FileUploadController {


    private final AwsS3Util s3Service;
    private final ApplicationProperties applicationProperties;

    // 파일 업로드 처리
    @PostMapping("upload")
    public ResponseEntity<?> upload(MultipartFile multipartFile) throws Exception {
        System.out.println(multipartFile);

        String originalFileName = multipartFile.getOriginalFilename();
        System.out.println(originalFileName);
        // ========= 파일명 중복 방지 처리 ========= //
        String uuidFileName = getUuidFileName(originalFileName);

        // ========= 서버에 파일 저장 ========= //
        String res = s3Service.saveFile(multipartFile, uuidFileName);
        System.out.println(res);
        return ResponseMessage.SuccessResponse("이미지 저장 성공", res);
    }

    private static String getUuidFileName(String originalFileName) {
        return UUID.randomUUID() + "_" + originalFileName;
    }
}
