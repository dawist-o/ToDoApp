package com.todoapp.notification.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl

@Configuration
class MailConfig {
    @Value("\${cloud.mail}")
    lateinit var mail: String

    @Value("\${cloud.password}")
    lateinit var pass: String
    /***
     * This method can send original emails, start OpenServer
     * For this:
     * 1) change username and password for real one
     * 2) change host to smtp.gmail.com for example
     * 3) change port for 25/465/587/2525 if doesnt work
     * */
    @Bean
    fun javaMailSender(): JavaMailSender {
        val javaMailSenderImpl = JavaMailSenderImpl()
        javaMailSenderImpl.apply {
            host = "smtp.gmail.com";
            port = 587
            username = mail;
            password = pass
        }
        val properties = javaMailSenderImpl.getJavaMailProperties()
        properties["mail.transport.protocol"] = "smtp"
        properties["mail.smtp.auth"] = "true"
        properties["mail.smtp.starttls.enable"] = "true"
        properties["mail.debug"] = "true"

        return javaMailSenderImpl
    }
}