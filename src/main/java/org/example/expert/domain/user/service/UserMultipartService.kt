package org.example.expert.domain.user.service

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import org.example.expert.domain.common.exception.InvalidRequestException
import org.example.expert.domain.common.util.FileUtil
import org.example.expert.domain.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.InputStreamResource
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.io.IOException

@Service
class UserMultipartService(
    private val amazonS3: AmazonS3,
    private val userRepository: UserRepository,
    @Value("\${cloud.aws.s3.bucket}")
    private val bucket: String
) {

    @Transactional
    @Throws(IOException::class)
    fun uploadImage(image: MultipartFile, userId: Long): String {
        val fileName = FileUtil.getRandomFileName(image) // 고유한 파일 이름 생성

        // 메타데이터 설정
        val metadata = ObjectMetadata()
        metadata.contentType = image.contentType
        metadata.contentLength = image.size

        // S3에 파일 업로드 요청 생성
        val putObjectRequest = PutObjectRequest(bucket, fileName, image.inputStream, metadata)

        // S3에 파일 업로드
        amazonS3.putObject(putObjectRequest)

        return changeProfile(userId, getPublicUrl(fileName))
    }

    fun downloadImage(fileName: String?): InputStreamResource {
        return InputStreamResource(
            amazonS3.getObject(bucket, fileName).objectContent
        )
    }

    protected fun changeProfile(userId: Long, url: String): String {
        val user = userRepository.findById(userId)
            .orElseThrow { InvalidRequestException("User not found") }
        user.addProfile(url)
        userRepository.save(user)
        return url
    }

    private fun getPublicUrl(fileName: String): String {
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucket, amazonS3.regionName, fileName)
    }
}