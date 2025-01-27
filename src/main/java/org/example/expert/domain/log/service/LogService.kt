package org.example.expert.domain.log.service

import org.example.expert.domain.log.entity.Log.Companion.of
import org.example.expert.domain.log.repository.LogRepository
import org.example.expert.domain.manager.dto.request.ManagerSaveRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class LogService(
    private val logRepository: LogRepository
) {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun saveLog(req: ManagerSaveRequest, errorMessage: String) {
        logRepository.save(of(req, errorMessage))
    }
}