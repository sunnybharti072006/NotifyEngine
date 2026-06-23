package com.NotifyEngine.repository

import com.NotifyEngine.model.DeadLetterEntry
import org.springframework.data.jpa.repository.JpaRepository

interface DeadLetterRepository: JpaRepository<DeadLetterEntry, String> {
    fun findAllByNotificationId(notificationId: String): List<DeadLetterEntry>
    fun findAllByChannel(channel: String): List<DeadLetterEntry>
}