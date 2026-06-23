package com.NotifyEngine.repository

import com.NotifyEngine.model.Notification
import jakarta.transaction.Status
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate
import java.time.LocalDateTime

interface NotificationRepository: JpaRepository<Notification, String> {
    fun findAllByTenantId(tenantId: String): List<Notification>
    fun findAllByStatus(status: Status): List<Notification>
    fun countByTenantIdAndStatus(tenantId: String, status: Status): Long
    fun findAllByScheduledAtBeforeAndStatus(
        scheduledBefore: LocalDateTime,
        status: Status,

    ): List<Notification>

}