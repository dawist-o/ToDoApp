package com.todoapp.customer.controller

import com.todoapp.customer.dao.CustomerRequest
import com.todoapp.customer.model.Customer
import com.todoapp.customer.service.CustomerService
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("customer")
class CustomerController(private val customerService: CustomerService) {

    private val logger = KotlinLogging.logger {}

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getHello(){
        logger.info("qwe")
        customerService.getHelloWorld()
    }

    @PutMapping
    fun createCustomer(@RequestBody customer: CustomerRequest) =
        customerService.saveCustomer(Customer())

    @GetMapping("/customerId")
    fun createCustomer(@PathVariable customerId: Long) = customerService.findCustomer(customerId)


}