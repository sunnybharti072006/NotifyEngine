package com.NotifyEngine.queue
import com.NotifyEngine.model.Notification
import org.springframework.stereotype.Component
import java.util.concurrent.PriorityBlockingQueue

class NotificationQueue(
    val n
)