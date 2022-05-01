package com.todoapp.customer.dao

data class TaskRequest (var id: Long, var title: String, var content: String)

data class DeleteTaskRequest (var id: Long)