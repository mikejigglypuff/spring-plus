package org.example.expert.domain.common.controller;

import com.sun.management.OperatingSystemMXBean;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.dto.ServerHealth;
import org.example.expert.domain.common.service.HealthCheckService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.lang.management.ManagementFactory;

@RestController
@RequiredArgsConstructor
public class HealthCheckController {
    private final HealthCheckService service;

    @GetMapping("/health")
    public ResponseEntity<ServerHealth> healthCheck() {
        return ResponseEntity.ok(
            new ServerHealth(
                service.isConnected(),
                new File("/"),
                Runtime.getRuntime(),
                (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()
            )
        );
    }
}
