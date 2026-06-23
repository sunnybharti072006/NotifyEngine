package com.NotifyEngine.repository

import com.NotifyEngine.model.WebhookConfig
import org.springframework.data.jpa.repository.JpaRepository

interface WebhookRepository : JpaRepository<WebhookConfig, String> {
    fun findAllByTenantIdAndIsActive(tenantId: String, isActive: Boolean): List<WebhookConfig>
}