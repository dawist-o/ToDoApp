package com.todoapp.customer.controller

import com.todoapp.customer.dao.DeleteTaskRequest
import com.todoapp.customer.dao.TaskRequest
import com.todoapp.customer.model.Task
import com.todoapp.customer.service.TasksService
import mu.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import java.security.Principal

@Controller
@RequestMapping("/tasks")
class TasksController(
    private val tasksService: TasksService
) {

    @GetMapping("/all")
    fun getAllTasks(principal: Principal) =
        ResponseEntity.ok().body(tasksService.getAllTasks(principal))

    @PostMapping("/create")
    fun createTask(principal: Principal) =
        ResponseEntity.ok().body(tasksService.createTask(principal))

    @PutMapping("/update")
    fun updateTask(principal: Principal, @RequestBody taskRequest: TaskRequest) =
        ResponseEntity.ok().body(tasksService.updateTask(taskRequest, principal))

    @DeleteMapping("/delete")
    fun deleteTask(principal: Principal, @RequestBody deleteReq: DeleteTaskRequest)
        = ResponseEntity.ok().body(tasksService.deleteTask(deleteReq.id, principal))

}