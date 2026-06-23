package com.NotifyEngine.service

import com.NotifyEngine.repository.RateLimitRepository
import com.NotifyEngine.model.RateLimitTracker
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class RateLimitService(
    private val rateLimitRepository: RateLimitRepository
) {

    private val MAX_NOTIFICATIONS_PER_MINUTE = 5

    fun isRateLimited(userId: String, tenantId: String): Boolean {
        val oneMinuteAgo = LocalDateTime.now().minusMinutes(1)
        val tracker = rateLimitRepository
            .findByUserIdAndTenantIdAndWindowStartAfter(userId, tenantId, oneMinuteAgo)

        return if (tracker.isPresent) {
            tracker.get().count >= MAX_NOTIFICATIONS_PER_MINUTE
        } else false
    }

    fun incrementCount(userId: String, tenantId: String) {
        val oneMinuteAgo = LocalDateTime.now().minusMinutes(1)
        val tracker = rateLimitRepository
            .findByUserIdAndTenantIdAndWindowStartAfter(userId, tenantId, oneMinuteAgo)

        if (tracker.isPresent) {
            val existing = tracker.get()
            existing.count += 1
            rateLimitRepository.save(existing)
        } else {
            val newTracker = RateLimitTracker().apply {
                this.userId = userId
                this.tenantId = tenantId
                this.count = 1
                this.windowStart = LocalDateTime.now()
            }
            rateLimitRepository.save(newTracker)
        }
    }
}