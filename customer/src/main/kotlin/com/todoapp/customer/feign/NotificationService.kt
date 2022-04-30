package com.todoapp.customer.feign

import com.todoapp.customer.dao.NotificationRequest
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping

@FeignClient("notificationservice")
interface NotificationService {
    @PutMapping
    fun sendNotification(req:NotificationRequest)
}