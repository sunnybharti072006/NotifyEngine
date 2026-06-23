package com.NotifyEngine.service

import com.NotifyEngine.dto.NotificationRequest
import com.NotifyEngine.dto.NotificationResponse
import com.NotifyEngine.model.Notification
import com.NotifyEngine.queue.NotificationQueue
import com.NotifyEngine.repository.NotificationRepository
import com.NotifyEngine.repository.TenantRepository
import com.NotifyEngine.repository.TemplateRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class NotificationService(
    private val notificationRepository: NotificationRepository,
    private val tenantRepository: TenantRepository,
    private val templateRepository: TemplateRepository,
    private val rateLimitService: RateLimitService,
    private val templateService: TemplateService,
    private val notificationQueue: NotificationQueue
) {

    fun sendNotification(tenantId: String, request: NotificationRequest): NotificationResponse {

        // Step 1: Tenant exist karta hai?
        tenantRepository.findById(tenantId)
            .orElseThrow { RuntimeException("Tenant not found: $tenantId") }

        // Step 2: Rate limit check — spam rokne ke liye
        if (rateLimitService.isRateLimited(request.userId, tenantId)) {
            throw RuntimeException("Rate limit exceeded for user: ${request.userId}")
        }

        // Step 3: Template exist karta hai aur placeholders sahi hain?
        val (mergedSubject, mergedBody) = templateService.mergeTemplate(
            tenantId = tenantId,
            templateKey = request.templateId,
            placeholders = request.placeholders
        )

        // Step 4: Har channel ke liye alag notification record banao
        val notificationIds = mutableListOf<String>()

        request.channels.forEach { channel ->
            val notification = Notification().apply {
                this.tenant = tenantRepository.findById(tenantId).get()
                this.template = templateRepository
                    .findByTenantIdAndTemplateKey(tenantId, request.templateId).get()
                this.recipient = request.recipient
                this.channel = channel
                this.status = "PENDING"
                this.priority = request.priority
                // Agar scheduledAt diya hai toh future mein bhejo
                this.scheduledAt = request.scheduledAt?.let {
                    LocalDateTime.parse(it)
                }
            }

            // Database mein save karo
            val saved = notificationRepository.save(notification)
            notificationIds.add(saved.id)

            // Queue mein daalo — async process hoga
            notificationQueue.enqueue(
                notification = saved,
                subject = mergedSubject,
                body = mergedBody
            )
        }

        // Step 5: Rate limit counter badhao
        rateLimitService.incrementCount(request.userId, tenantId)

        // Step 6: 202 Accepted return karo — turant response
        // Client wait nahi karega — background mein process hoga
        return NotificationResponse(
            notificationId = notificationIds.joinToString(","),
            status = "QUEUED",
            message = "Notification queued successfully",
            channels = request.channels
        )
    }
}