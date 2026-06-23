package com.NotifyEngine.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "dead_letter_queue")

class DeadLetterEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String = ""

    @Column(nullable = false)
    var notificationId: String = ""

    @Column(nullable = false)
    var recipient: String = ""

    @Column(nullable = false)
    var channel: String = ""

    @Column(nullable = false, columnDefinition = "TEXT")
    var failureReason: String = ""

    @Column(nullable = false)
    var retryCount: Int = 0

    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()



}