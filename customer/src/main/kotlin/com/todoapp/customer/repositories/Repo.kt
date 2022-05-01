package com.todoapp.customer.repositories

import com.todoapp.customer.model.Customer
import com.todoapp.customer.model.Role
import com.todoapp.customer.model.Task
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface CustomerRepository : JpaRepository<Customer, Long>{
    fun findCustomerByEmail(email:String):Customer?

    fun existsByEmail(email: String):Boolean

    @Transactional
    @Modifying
    @Query(
        "UPDATE Customer c " +
                "SET c.enabled = TRUE WHERE c.email = ?1"
    )
    fun confirmRegister(email:String)
}

@Repository
interface RoleRepository : JpaRepository<Role, Long>{
    fun findByName(email: String):Role?
}

@Repository
interface TaskRepository : JpaRepository<Task, Long>