package com.NotifyEngine.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "rate_limit_tracker")

class RateLimitTracker {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String = ""

    @Column(nullable = false)
    var userId: String = ""

    @Column(nullable = false)
    var tenantId: String = ""

    @Column(nullable = false)
    var count: Int = 0

    @Column(nullable = false)
    var windowStart: LocalDateTime = LocalDateTime.now()
}