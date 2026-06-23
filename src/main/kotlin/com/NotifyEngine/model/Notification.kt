package com.NotifyEngine.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "notifications")

class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String = ""

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    var tenant: Tenant = Tenant()

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id", nullable = false)
    var template: Template = Template()

    // Recipient  email/phone
    @Column(nullable = false)
    var recipient: String = ""

    // EMAIL / SMS / WHATSAPP
    @Column(nullable = false)
    var channel: String = ""

    // PENDING / SENT / FAILED
    @Column(nullable = false)
    var status: String = "PENDING"

    // CRITICAL / HIGH / NORMAL / LOW
    @Column(nullable = false)
    var priority: String = "NORMAL"

    @Column(nullable = false)
    var retryCount: Int = 0

    var scheduledAt: LocalDateTime? = null


    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()

    var sentAt: LocalDateTime? = null

}
