package com.NotifyEngine.dto

data class ApiKeyResponse(
    val id: String,
    val keyValue: String,
    val isActive: Boolean,
    val createdAt: String
)