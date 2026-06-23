package com.NotifyEngine.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class TenantRequest(

    @field:NotBlank(message = "name required")
    val name: String,

    @field:Email(message = "valid email required")
    @field:NotBlank(message = "email required")
    val email: String
)