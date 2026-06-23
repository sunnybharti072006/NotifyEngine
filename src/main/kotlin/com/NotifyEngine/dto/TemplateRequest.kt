package com.NotifyEngine.dto

import jakarta.validation.constraints.NotBlank

data class TemplateRequest(

    @field:NotBlank(message = "templateKey required")
    val templateKey: String,

    @field:NotBlank(message = "channel required")
    val channel: String,

    val subject: String? = null,

    @field:NotBlank(message = "body required")
    val body: String
)