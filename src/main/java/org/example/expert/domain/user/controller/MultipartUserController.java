package org.example.expert.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.user.service.UserMultipartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/files")
public class MultipartUserController {
    private final UserMultipartService service;

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(
        @AuthenticationPrincipal AuthUser authUser, @RequestParam("file") MultipartFile file
    ) throws IOException {
        return ResponseEntity.ok("파일 업로드 성공: " + service.uploadImage(file, authUser.getId()));
    }
}
