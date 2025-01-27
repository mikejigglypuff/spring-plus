package org.example.expert.domain.common.service

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Service

@Service
class HealthCheckService(
    @PersistenceContext
    private val entityManager: EntityManager
) {
    val isConnected: Boolean
        get() {
            return try {
                entityManager.createNativeQuery("SELECT 1").singleResult != null
            } catch (e: Exception) {
                false
            }
        }
}