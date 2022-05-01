package com.todoapp.customer.service

import org.springframework.stereotype.Service
import java.util.function.Predicate
import java.util.regex.Pattern

@Service
class EmailValidator : Predicate<String> {
    override fun test(s: String): Boolean =
        Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}").matcher(s).matches()
}