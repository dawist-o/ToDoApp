package com.todoapp.customer.service

import com.todoapp.customer.dao.NotificationResponse
import com.todoapp.customer.model.Customer
import com.todoapp.customer.repository.CustomerRepository
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class CustomerService(
    private val customerRepository: CustomerRepository,
    private val restTemplate: RestTemplate
) {

    fun getNotification():String{
        var response = restTemplate.getForObject(
            "http://localhost:8081/notification",
            NotificationResponse::class.java
        )
        return response?.notification ?: "Service not responding"
    }

    fun saveCustomer(customer: Customer):Customer = customerRepository.save(customer)

    fun findCustomer(id: Long):Customer = customerRepository.getById(id)
}