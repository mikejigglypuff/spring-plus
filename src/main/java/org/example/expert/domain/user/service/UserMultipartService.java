package org.example.expert.domain.user.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserMultipartService {
    private final AmazonS3 amazonS3;
    private final UserRepository userRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional
    public String uploadImage(MultipartFile image, Long userId) throws IOException {
        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename(); // 고유한 파일 이름 생성

        // 메타데이터 설정
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(image.getContentType());
        metadata.setContentLength(image.getSize());

        // S3에 파일 업로드 요청 생성
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, fileName, image.getInputStream(), metadata);

        // S3에 파일 업로드
        amazonS3.putObject(putObjectRequest);

        return changeProfile(userId, getPublicUrl(fileName));
    }

    public InputStreamResource downloadImage(String fileName) {
        return new InputStreamResource(
            amazonS3.getObject(bucket, fileName).getObjectContent());
    }

    protected String changeProfile(long userId, String url) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new InvalidRequestException("User not found"));
        user.addProfile(url);
        userRepository.save(user);
        return url;
    }

    private String getPublicUrl(String fileName) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucket, amazonS3.getRegionName(), fileName);
    }
}
