package com.NotifyEngine.dto

data class AnalyticsResponse(
    val totalSent: Long,
    val totalFailed: Long,
    val totalPending: Long,
    val successRate: Double
)