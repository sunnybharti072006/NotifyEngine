package com.NotifyEngine.dto

data class NotificationResponse(
    val notificationId: String,
    val status: String,
    val message: String,
    val channels: List<String>
)