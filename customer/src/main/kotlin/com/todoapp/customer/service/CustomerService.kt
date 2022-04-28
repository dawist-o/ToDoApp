package com.todoapp.customer.service

import com.todoapp.customer.model.Customer
import com.todoapp.customer.repository.CustomerRepository
import org.springframework.stereotype.Service

@Service
class CustomerService(private val customerRepository: CustomerRepository) {

    fun getHelloWorld() = "hello world"

    fun saveCustomer(customer: Customer):Customer = customerRepository.save(customer)

    fun findCustomer(id: Long):Customer = customerRepository.getById(id)
}