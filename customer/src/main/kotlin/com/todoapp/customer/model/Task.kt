package com.todoapp.customer.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "task")
open class Task(open var title:String, open var content:String){
    @get:Id
    @get:GeneratedValue
    @get:Column(name = "task_id")
    open var id:Long? = null

    @get:ManyToOne(fetch = FetchType.LAZY, optional = false)
    @get:JoinColumn(name = "fk_customer_id")
    @JsonIgnore
    open var customer: Customer? = null
}
