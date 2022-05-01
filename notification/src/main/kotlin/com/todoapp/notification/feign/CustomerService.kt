package com.todoapp.notification.feign

import com.todoapp.notification.rest.ConfirmRegisterRequest
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PutMapping

@FeignClient(name = "customer-service")
interface CustomerService {
    @PutMapping("/customer/confirm")
    fun sendRegisterNotification(email: ConfirmRegisterRequest)
}