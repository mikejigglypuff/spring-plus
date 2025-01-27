package org.example.expert.domain.common.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class Timestamped {
    @CreatedDate
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private var createdAt: LocalDateTime? = null

    @LastModifiedDate
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private var modifiedAt: LocalDateTime? = null

    fun getCreatedAt() : LocalDateTime? { return createdAt }
    fun getModifiedAt() : LocalDateTime? { return modifiedAt }

    fun setCreatedAt(createdAt : LocalDateTime?) : Unit { this.createdAt = createdAt }
    fun setModifiedAt(modifiedAt : LocalDateTime?) : Unit { this.createdAt = modifiedAt }
}