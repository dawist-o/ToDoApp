package com.todoapp.customer

import com.todoapp.customer.dao.CustomerRequest
import com.todoapp.customer.model.Customer
import com.todoapp.customer.model.Role
import com.todoapp.customer.service.CustomerService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
class CustomerApplication{

    //create sql file
    @Bean
    fun init(customerService: CustomerService) = CommandLineRunner {
        customerService.saveRole(Role("ROLE_USER"))
        customerService.saveRole(Role("ROLE_ADMIN"))

        customerService.saveCustomer(CustomerRequest("1","1"))
        customerService.saveCustomer(CustomerRequest("2","2"))
        customerService.saveCustomer(CustomerRequest("3","3"))
        customerService.saveCustomer(CustomerRequest("4","4"))

    }
}

fun main(args: Array<String>) {
    runApplication<CustomerApplication>(*args)
}

