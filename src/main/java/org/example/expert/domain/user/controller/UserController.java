package org.example.expert.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.user.dto.request.UserChangePasswordRequest;
import org.example.expert.domain.user.dto.response.UserResponse;
import org.example.expert.domain.user.service.UserBulkInsertService;
import org.example.expert.domain.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserBulkInsertService bulkInsertService;

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable long userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getUserByNickname(@RequestParam String nickname) {
        return ResponseEntity.ok(userService.getUserByNickname(nickname));
    }

    @PutMapping("/users")
    public void changePassword(
        @AuthenticationPrincipal AuthUser authUser, @RequestBody UserChangePasswordRequest userChangePasswordRequest
    ) {
        userService.changePassword(authUser.getId(), userChangePasswordRequest);
    }

    @PostMapping("/users/bulk/{repeats}")
    public ResponseEntity<String> insertBulkUser(@PathVariable int repeats) {
        bulkInsertService.batchInsert(repeats, 1000);
        return ResponseEntity.ok("created " + repeats + " users");
    }
}
