package com.woowahan.smell.bazzangee.aws;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.woowahan.smell.bazzangee.repository.contents.ReviewRepository;
import com.woowahan.smell.bazzangee.repository.food.OrderFoodRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;
    private final String HONEY_COMBO_IMGURL = "https://s3.ap-northeast-2.amazonaws.com/baezzangee/static/default/default_chicken.png";

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private OrderFoodRepository orderFoodRepository;

    public String upload(MultipartFile multipartFile, String dirName, String existImageUrl, Long orderFoodId) throws IOException {
        log.info("multipartFile : {}, existImageUrl : {}", multipartFile, existImageUrl);
        if(multipartFile == null && existImageUrl == null) {
            return orderFoodRepository.findById(orderFoodId).orElseThrow(() -> new IllegalArgumentException("해당 주문내역이 존재하지 않습니다"))
                    .getFood().getRestaurant().getFoodCategory().getImageUrl();
        }

        if(multipartFile == null && !StringUtils.isBlank(existImageUrl)) {
            return existImageUrl;
        }

        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));

        return upload(uploadFile, dirName);
    }

    private String upload(File uploadFile, String dirName) {
        // 오리지널 이름 : uploadFile.getName();
        // 저장 이름 : saved file name

        String fileName = dirName + "/" + System.currentTimeMillis();
        String uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile);

        return uploadImageUrl;
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(file.getOriginalFilename());
        if(convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }

        return Optional.empty();
    }
}