package com.todoapp.customer.model

import javax.persistence.*

@Entity
@Table(name = "сustomer")
open class Customer(open var email: String,open var  pass: String, open var enabled: Boolean = false) {

    @get:Id
    @get:GeneratedValue(strategy = GenerationType.AUTO)
    @get:Column(name = "customer_id")
    open var id:Long? = null

    @get:OneToMany(cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY, mappedBy = "customer")
    open var tasks:MutableList<Task>? = mutableListOf()

    @get:ManyToMany(fetch = FetchType.EAGER)
    open var roles:MutableList<Role>? = mutableListOf()

}