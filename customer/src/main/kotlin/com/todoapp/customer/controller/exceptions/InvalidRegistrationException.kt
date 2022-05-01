package com.todoapp.customer.controller.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "There is no such user")
class InvalidRegistrationException(message: String): RuntimeException(message) {
}