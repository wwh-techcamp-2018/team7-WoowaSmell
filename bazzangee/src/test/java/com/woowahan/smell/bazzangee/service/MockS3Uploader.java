package com.woowahan.smell.bazzangee.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.woowahan.smell.bazzangee.aws.S3Uploader;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class MockS3Uploader extends S3Uploader {

    public MockS3Uploader(AmazonS3Client amazonS3Client) {
        super(amazonS3Client);
    }

    @Override
    public String upload(MultipartFile multipartFile, String dirName, String existImageUrl) throws IOException {
        return "www.naver.com";
    }
}
