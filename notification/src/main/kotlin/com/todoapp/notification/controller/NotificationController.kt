package com.todoapp.notification.controller

import com.todoapp.notification.rest.RegisterRequest
import com.todoapp.notification.service.NotificationService
import mu.KotlinLogging
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest


@RestController
@RequestMapping("/notification")
class NotificationController(
    private val notificationService: NotificationService
) {

    private val logger = KotlinLogging.logger {}

    @GetMapping("/cloudConfig")
    fun returnInformationFromCloud() = notificationService.mail + notificationService.pass

    @PostMapping("/register")
    fun sendNotification(@RequestBody notification: RegisterRequest, request: HttpServletRequest) {
        notificationService.sendEmail(notification.email, request)
    }

    @GetMapping("/confirm")
    fun confirmRegistration(@RequestParam token:String){
        notificationService.confirmRegistration(token)
    }
}