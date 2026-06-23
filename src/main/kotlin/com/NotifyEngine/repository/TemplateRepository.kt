package com.NotifyEngine.repository

import com.NotifyEngine.model.Template
import com.NotifyEngine.model.Tenant
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface TemplateRepository: JpaRepository<Template, String> {

    fun findByTenantIdAndTemplateKey(tenantId: String, templateId: String): Optional<Template>
    fun findAllByTenantId(tenantId: String): List<Template>
    fun existsByTenantIdAndTemplateKey(tenantId: String, templateKey: String): Boolean
}