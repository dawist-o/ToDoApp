package com.todoapp.customer.controller

import com.todoapp.customer.dao.CustomerRequest
import com.todoapp.customer.dao.NotificationRequest
import com.todoapp.customer.model.Customer
import com.todoapp.customer.service.CustomerService
import com.todoapp.customer.service.TokenService
import mu.KotlinLogging
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
@RequestMapping("/customer")
class CustomerController(
    private val customerService: CustomerService,
) {

    private val logger = KotlinLogging.logger {}

    @GetMapping("/users")
    fun getUsers() = ResponseEntity.ok().body(customerService.getAllCustomers())

    @PostMapping("/login")
    fun loginUser(@RequestBody customer: CustomerRequest) =
        ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                customerService.saveCustomer(customer)
            )

    @PutMapping("/register")
    fun registerUser(@RequestBody customer: CustomerRequest) =
        ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                customerService.registerCustomer(customer)
            )

    @PutMapping("/confirm")
    fun registerUser(@RequestBody emailToConfirm: NotificationRequest) =
        ResponseEntity
            .status(HttpStatus.ACCEPTED)
            .body(
                customerService.confirmRegister(emailToConfirm.email)
            )


}