package com.NotifyEngine.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty

data class NotificationRequest(

    @field:NotBlank(message = "userId required")
    val userId: String,

    @field:NotBlank(message = "recipient required")
    val recipient: String,

    @field:NotEmpty(message = "atleast one channel required")
    val channels: List<String>,

    @field:NotBlank(message = "templateId required")
    val templateId: String,

    val placeholders: Map<String, String> = emptyMap(),

    // CRITICAL / HIGH / NORMAL / LOW
    val priority: String = "NORMAL",

    // Future scheduling — null means abhi bhejo
    val scheduledAt: String? = null
)