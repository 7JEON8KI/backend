package com.hyundai.global.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * @author : 강은구
 * @since : 02/19/2024
 */
@Configuration
@EnableWebMvc
@RequiredArgsConstructor
public class S3Config implements WebMvcConfigurer {

    private final ApplicationProperties applicationProperties;

    public BasicAWSCredentials AwsCredentials() {
        BasicAWSCredentials AwsCreds = new BasicAWSCredentials(applicationProperties.getAWS_ACCESS_KEY(), applicationProperties.getAWS_SECRET_KEY());
        return AwsCreds;
    }

    @Bean
    public AmazonS3 AwsS3Client() {

        AmazonS3 s3Builder = AmazonS3ClientBuilder.standard()
                .withRegion(applicationProperties.getAWS_REGION())
                .withCredentials(new AWSStaticCredentialsProvider(this.AwsCredentials()))
                .build();

        return s3Builder;
    }
}
