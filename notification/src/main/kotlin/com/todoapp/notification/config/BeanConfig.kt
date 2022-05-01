package com.todoapp.notification.config

import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class BeanConfig {
    @Bean
    @LoadBalanced
    fun restTemplate() = RestTemplate()
}