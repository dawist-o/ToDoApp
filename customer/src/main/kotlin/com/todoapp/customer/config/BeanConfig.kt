package com.todoapp.customer.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Configuration
class BeanConfig {
    @Bean
    @LoadBalanced
    fun restTemplate() = RestTemplate()

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()
}
