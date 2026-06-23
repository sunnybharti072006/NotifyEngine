package com.NotifyEngine.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "templates")
class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id : String = ""

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    var tenant: Tenant = Tenant()

    @Column(nullable = false)
    var templateKey: String = ""

    @Column(nullable = false)
    var channel: String = ""

    var subject: String? = null

    @Column(nullable = false, columnDefinition = "TEXT")
    var body: String = ""

    @Column(nullable = false)
    var isActive: Boolean = true

    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()

}