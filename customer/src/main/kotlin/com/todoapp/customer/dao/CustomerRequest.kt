package com.todoapp.customer.dao

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.databind.annotation.JsonNaming

data class CustomerRequest(
    @JsonProperty("email")  val email:String,
    @JsonProperty("password")  val password:String
)
