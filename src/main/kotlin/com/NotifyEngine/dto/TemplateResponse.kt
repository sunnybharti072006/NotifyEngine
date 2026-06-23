package com.NotifyEngine.dto

data class TemplateResponse(
    val id: String,
    val templateKey: String,
    val channel: String,
    val subject: String?,
    val body: String,
    val isActive: Boolean
)