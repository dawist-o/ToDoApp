package com.todoapp.notification.model

import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "token")
open class ConfirmationToken(
    open var email: String,
    open var token: String,
    open var confirmed: Boolean = false
) {
    @get:Id
    @get:GeneratedValue(strategy = GenerationType.AUTO)
    @get:Column(name = "token_id")
    open var id: Long? = null


}