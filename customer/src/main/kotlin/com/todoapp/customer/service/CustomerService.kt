package com.todoapp.customer.service

import com.todoapp.customer.controller.exceptions.InvalidRegistrationException
import com.todoapp.customer.dao.CustomerRequest
import com.todoapp.customer.dao.NotificationRequest
import com.todoapp.customer.feign.NotificationService
import com.todoapp.customer.model.Customer
import com.todoapp.customer.model.Role
import com.todoapp.customer.repositories.CustomerRepository
import com.todoapp.customer.repositories.RoleRepository
import com.todoapp.customer.repositories.TaskRepository
import mu.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestTemplate
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import java.util.regex.Pattern

@Service
@Transactional
class CustomerService(
    private val customerRepository: CustomerRepository,
    private val roleRepository: RoleRepository,
    private val emailValidator: EmailValidator,
    private val passwordEncoder: PasswordEncoder,
    private val notificationService: NotificationService
) : UserDetailsService {

    private val logger = KotlinLogging.logger {}

    override fun loadUserByUsername(username: String): UserDetails {
        logger.info("LOAD USER BY USER NAME")
        val customer = customerRepository.findCustomerByEmail(username)
            ?: throw UsernameNotFoundException("Customer with name: ${username} not found ")

        val authorities = mutableListOf<SimpleGrantedAuthority>()
        customer.roles?.map { authorities.add(SimpleGrantedAuthority(it.name)) }

        return User(customer.email, customer.pass, authorities)
    }

    fun saveCustomer(customerRequest: CustomerRequest) {
        // todo some checks for email pattern and existing user
        val customer = customerRepository.save(
            Customer(
                customerRequest.email,
                passwordEncoder.encode(customerRequest.password)
            )
        )
        val role = roleRepository.findByName("ROLE_USER")
            ?: throw UsernameNotFoundException("Role not found:ROLE_USER")
        customer.roles?.add(role)
    }

    fun registerCustomer(customerRequest: CustomerRequest) {
        if (!emailValidator.test(customerRequest.email))
            throw InvalidRegistrationException("Email is not valid")
        if (customerRepository.existsByEmail(customerRequest.email))
            throw InvalidRegistrationException("Email is already taken")

        val customer = customerRepository.save(
            Customer(
                customerRequest.email,
                passwordEncoder.encode(customerRequest.password)
            )
        )
        val role = roleRepository.findByName("ROLE_USER")
            ?: throw InvalidRegistrationException("Role not found:ROLE_USER")

        customer.roles?.add(role)

        notificationService.sendRegisterNotification(NotificationRequest(customer.email))
    }

    fun saveRole(role: Role) = roleRepository.save(role)

    fun addRoleToCustomer(email: String, role: String) {
        val customer = customerRepository.findCustomerByEmail(email)
            ?: throw UsernameNotFoundException("Customer with name: ${email} not found ")
        val role = roleRepository.findByName(role)
            ?: throw UsernameNotFoundException("Role not found: ${role}")
        customer.roles?.add(role)
    }

    fun getAllCustomers() = customerRepository.findAll()

    fun confirmRegister(email: String) {
        customerRepository.confirmRegister(email)
    }
}