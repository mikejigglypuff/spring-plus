package org.example.expert.domain.common.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

@Service
public class HealthCheckService {
    @PersistenceContext
    private EntityManager entityManager;

    public boolean isConnected() {
        try {
            return entityManager.createNativeQuery("SELECT 1").getSingleResult() != null;
        } catch (Exception e) {
            return false;
        }
    }
}
