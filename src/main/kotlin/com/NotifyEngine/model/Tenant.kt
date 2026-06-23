package com.NotifyEngine.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "Tenants")
 class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id : String = ""

    @Column(nullable = false,unique = true)
    var name : String = ""

    @Column(nullable = false, unique = true)
    var email : String = ""

    @Column(nullable = false)
    var isActive : Boolean = true

    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()

}