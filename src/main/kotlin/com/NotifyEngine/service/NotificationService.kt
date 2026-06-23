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


        tenantRepository.findById(tenantId)
            .orElseThrow { RuntimeException("Tenant not found: $tenantId") }


        if (rateLimitService.isRateLimited(request.userId, tenantId)) {
            throw RuntimeException("Rate limit exceeded for user: ${request.userId}")
        }


        val (mergedSubject, mergedBody) = templateService.mergeTemplate(
            tenantId = tenantId,
            templateKey = request.templateId,
            placeholders = request.placeholders
        )

       
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

                this.scheduledAt = request.scheduledAt?.let {
                    LocalDateTime.parse(it)
                }
            }


            val saved = notificationRepository.save(notification)
            notificationIds.add(saved.id)


            notificationQueue.enqueue(
                notification = saved,
                subject = mergedSubject,
                body = mergedBody
            )
        }


        rateLimitService.incrementCount(request.userId, tenantId)



        return NotificationResponse(
            notificationId = notificationIds.joinToString(","),
            status = "QUEUED",
            message = "Notification queued successfully",
            channels = request.channels
        )
    }
}