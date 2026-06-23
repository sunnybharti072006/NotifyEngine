package com.NotifyEngine.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "webhook_configs")

class WebhookConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String = ""

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    var tenant: Tenant = Tenant()

    @Column(nullable = false)
    var url: String = ""

    @Column(nullable = false)
    var triggerOn: String = "ALL"

    @Column(nullable = false)
    var isActive: Boolean = true

    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()

}