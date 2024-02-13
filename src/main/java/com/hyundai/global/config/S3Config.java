package com.hyundai.global.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class S3Config implements WebMvcConfigurer {
    @Autowired
    private ApplicationProperties applicationProperties;
    @Bean
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
