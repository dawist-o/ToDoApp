package com.todoapp.notification.rest

data class RegisterRequest(val email: String)

data class ConfirmRegisterRequest(val email: String)
