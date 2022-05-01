package com.todoapp.notification.service

import com.todoapp.notification.feign.CustomerService
import com.todoapp.notification.model.ConfirmationToken
import com.todoapp.notification.repo.TokenRepository
import com.todoapp.notification.rest.ConfirmRegisterRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import java.util.*
import javax.servlet.http.HttpServletRequest


@Service
class NotificationService(
    private val templateEngine: TemplateEngine,
    private val mailSender: JavaMailSender,
    private val tokenRepository: TokenRepository,
    private val customerService: CustomerService,
    private val restTemplate: RestTemplate
) {
    @Value("\${cloud.mail}")
    lateinit var mail: String

    @Value("\${cloud.password}")
    lateinit var pass: String

    // config OpenServer
    @Async
    fun sendEmail(emailTo: String, request: HttpServletRequest) {
        //put args into html message
        val context = Context(Locale.ENGLISH)
        val link = getLink(emailTo, request)
        context.setVariable("link", link)
        val html = templateEngine.process("emails/email", context)

        // Create a default MimeMessage object.
        val mimeMessage = mailSender.createMimeMessage()
        val messageHelper = MimeMessageHelper(mimeMessage, true, "UTF-8")

        messageHelper.setTo(emailTo)
        messageHelper.setText(html, true)
        println("sending...")
        mailSender.send(mimeMessage)
    }

    fun getLink(emailTo: String, request: HttpServletRequest): String {
        val token = UUID.randomUUID().toString()
        val confirmationToken =
            ConfirmationToken(emailTo, token , false)
        tokenRepository.save(confirmationToken)
        val address: String = request.requestURL.toString().replace(request.requestURI, "")
        return "$address/notification/confirm?token=$token"
    }

    fun confirmRegistration(token: String) {
        check(tokenRepository.existsByToken(token)){"token not found"}

        val tokenEntity = tokenRepository.findByToken(token)

        check(!tokenEntity.confirmed){"email already confirmed"}

        customerService.sendRegisterNotification(ConfirmRegisterRequest(tokenEntity.email))

        tokenRepository.updateConfirmed(token)
    }

}


