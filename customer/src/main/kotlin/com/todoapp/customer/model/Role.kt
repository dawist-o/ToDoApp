package com.todoapp.customer.model

import javax.persistence.*

@Entity
@Table(name = "role")
open class Role(open var name:String) {
    @get:Id
    @get:GeneratedValue(strategy = GenerationType.AUTO)
    open var id:Long? = null
}