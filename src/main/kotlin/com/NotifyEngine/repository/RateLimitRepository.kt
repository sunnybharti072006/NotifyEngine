package com.NotifyEngine.repository

import com.NotifyEngine.model.RateLimitTracker
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime
import java.util.Optional

interface RateLimitRepository : JpaRepository<RateLimitTracker, String> {
    fun findByUserIdAndTenantIdAndWindowStartAfter(
        userId: String,
        tenantId: String,
        windowStart: LocalDateTime
    ): Optional<RateLimitTracker>
}