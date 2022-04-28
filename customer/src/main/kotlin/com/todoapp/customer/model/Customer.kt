package com.todoapp.customer.model

import javax.persistence.*

@Entity
@Table(name = "customer")
open class Customer {

    @get:Id
    @get:SequenceGenerator(name = "customer_id_sequence", sequenceName = "customer_id_sequence")
    @get:GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_id_sequence")
    @get:Column(name = "id")
    open var id:Long? = null

    open var login:String? = null

    open var password:String? = null
}

//@Entity
//@Table(name = "customer")
//data class Customer(@Id val id: Long? = null, var login:String, var password:String)