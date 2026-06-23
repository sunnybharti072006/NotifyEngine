package com.NotifyEngine.repository

import com.NotifyEngine.model.ApiKey
import com.NotifyEngine.model.Tenant
import io.micrometer.common.KeyValues
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface ApiKeyRepository: JpaRepository<ApiKey, String> {
    fun findByKeyValue(keyValues: String): Optional<ApiKey>
    fun  findAllByTenantId(tenantId: String): List<Tenant>
    fun existsByKeyValue(keyValue: String): Boolean
}