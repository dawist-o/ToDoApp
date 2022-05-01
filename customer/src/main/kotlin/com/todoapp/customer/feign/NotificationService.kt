package com.todoapp.customer.feign

import com.todoapp.customer.dao.NotificationRequest
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping

@FeignClient(name = "notification-service")
interface NotificationService {
    @PostMapping("/notification/register")
    fun sendRegisterNotification(req: NotificationRequest)
}