package com.todoapp.customer.service

import com.todoapp.customer.dao.TaskRequest
import com.todoapp.customer.model.Role
import com.todoapp.customer.model.Task
import com.todoapp.customer.repositories.CustomerRepository
import com.todoapp.customer.repositories.TaskRepository
import mu.KotlinLogging
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.security.Principal

@Service
class TasksService(
    private val taskRepository: TaskRepository,
    private val customerRepository: CustomerRepository
) {
    private val logger = KotlinLogging.logger {}

    fun getAllTasks(principal: Principal): MutableList<Task>? {
        logger.info(principal.name)
        val customer = findCustomerFromPrincipal(principal)
            ?: throw UsernameNotFoundException("Customer not found by principal")
        return customer.tasks
    }

    fun deleteTask(id: Long, principal: Principal) = taskRepository.deleteById(id)

    fun updateTask(taskRequest: TaskRequest, principal: Principal):Task {
        val entity = taskRepository.findById(taskRequest.id).get()
        entity.title = taskRequest.title
        entity.content = taskRequest.content
        taskRepository.flush()
        return entity
    }

    fun createTask(principal: Principal):Task {
        logger.info(principal.name)
        val customer = findCustomerFromPrincipal(principal)
            ?: throw UsernameNotFoundException("Customer not found by principal")
        customerRepository.flush()
        val task = Task("", "")
        task.customer = customer
        taskRepository.save(task)
        taskRepository.flush()
        return task
    }

    fun findCustomerFromPrincipal(principal: Principal) = customerRepository.findCustomerByEmail(principal.name)

}