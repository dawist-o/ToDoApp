package com.todoapp.notification.controller

import com.todoapp.notification.rest.NotificationResponse
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/notification")
class NotificationController {

    private val logger = KotlinLogging.logger {}

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    fun getHelloFromNotificationsService():NotificationResponse{
        val notification = "Notification from service"
        logger.info(notification)
        return NotificationResponse(notification)
    }
}