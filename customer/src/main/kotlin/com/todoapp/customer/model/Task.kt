package com.todoapp.customer.model

import javax.persistence.*

@Entity
@Table(name = "task")
open class Task{
    @get:Id
    @get:GeneratedValue
    @get:Column(name = "task_id")
    open var id:Long? = null

    open var title:String? = null

    open var content:String? = null

    @get:ManyToOne(fetch = FetchType.LAZY, optional = false)
    @get:JoinColumn(name = "fk_customer_id")
    open var customer: Customer? = null
}
