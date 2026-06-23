package com.NotifyEngine.repository

import com.NotifyEngine.model.Tenant
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface TenantRepository : JpaRepository<Tenant, String> {

    fun findByEmail(email: String): Optional<Tenant>
    fun findByName(name: String): Optional<Tenant>
    fun existsByEmail(email: String): Boolean
}