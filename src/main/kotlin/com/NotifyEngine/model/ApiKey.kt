package com.NotifyEngine.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "api_keys")
class ApiKey {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String = ""

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    val tenant: Tenant = Tenant()

    @Column(nullable = false, unique  = true)
    val keyValue: String = ""

    @Column(nullable = false)
    val isActive: Boolean = true


    @Column(nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()

    val expiresAt: LocalDateTime? = null
}