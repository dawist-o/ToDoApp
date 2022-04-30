package com.todoapp.customer.model

import javax.persistence.*

@Entity
@Table(name = "сustomer")
open class Customer {

    @get:Id
    @get:SequenceGenerator(name = "customer_id_sequence", sequenceName = "customer_id_sequence")
    @get:GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_id_sequence")
    @get:Column(name = "customer_id")
    open var id:Long? = null

    open var login:String? = null

    open var password:String? = null

    @get:OneToMany(cascade = [(CascadeType.ALL)], mappedBy = "customer")
    open var tasks:MutableList<Task>? = mutableListOf()
}