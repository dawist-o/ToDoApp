package com.todoapp.customer.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.databind.ObjectMapper
import com.todoapp.customer.model.Role
import com.todoapp.customer.repositories.CustomerRepository
import com.todoapp.customer.security.CloudConfigProperties
import mu.KotlinLogging
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Service
class TokenService(private val customerRepository: CustomerRepository) {

    private val logger = KotlinLogging.logger {}

    fun refreshToken(
        request: HttpServletRequest,
        response: HttpServletResponse
    ) {

        val header = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (header != null && header.startsWith(CloudConfigProperties.prefix)) {
            try {
                val refreshToken = header.substringAfter(CloudConfigProperties.prefix)
                val algorithm = Algorithm.HMAC256(CloudConfigProperties.salt.toByteArray())
                val verifier = JWT.require(algorithm).build()
                val decodedJWT = verifier.verify(refreshToken)

                val customer = customerRepository.findCustomerByEmail(decodedJWT.subject)

                val accessToken = JWT.create()
                    .withSubject(customer!!.email)
                    .withExpiresAt(Date(System.currentTimeMillis() + 2 * 60 * 1000))
                    .withIssuer(request.requestURI)
                    .withClaim(
                        CloudConfigProperties.claim,
                        customer.roles!!.stream().map { it.name }.collect(Collectors.toList())
                    )
                    .sign(algorithm)

                val tokens = mapOf("access_token" to accessToken, "refresh_token" to refreshToken)
                response.contentType = MediaType.APPLICATION_JSON_VALUE
                ObjectMapper().writeValue(response.outputStream, tokens)
            } catch (e: Exception) {
                logger.error("Error login in: ${e.message}")
                response.apply {
                    status = HttpStatus.FORBIDDEN.value()
                    contentType = MediaType.APPLICATION_JSON_VALUE
                }
                ObjectMapper().writeValue(response.outputStream, mapOf("error_message" to e.message))
            }
        } else {
            throw RuntimeException("Refresh token is missing")
        }
    }
}