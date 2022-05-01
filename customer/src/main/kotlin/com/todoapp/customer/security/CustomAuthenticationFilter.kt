package com.todoapp.customer.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.databind.ObjectMapper
import com.todoapp.customer.dao.CustomerRequest
import com.todoapp.customer.security.CloudConfigProperties.claim
import com.todoapp.customer.security.CloudConfigProperties.salt
import mu.KotlinLogging
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.util.*
import java.util.stream.Collectors
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomAuthenticationFilter(
    private val authManager: AuthenticationManager
) : UsernamePasswordAuthenticationFilter() {

    private val logger = KotlinLogging.logger {}

    init {
        usernameParameter = "email"
    }


    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        val customerRequest = ObjectMapper().readValue(request.inputStream, CustomerRequest::class.java)
        logger.warn(customerRequest.toString())

        return authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                customerRequest.email,
                customerRequest.password
            )
        )
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        val user = authResult.principal as User
        //TODO salt from cloud
        val algorithm = Algorithm.HMAC256(salt.toByteArray())

        // 5 minutes
        val accessToken = JWT.create()
            .withSubject(user.username)
            .withExpiresAt(Date(System.currentTimeMillis() + 2 * 60 * 1000))
            .withIssuer(request.requestURI)
            .withClaim(
                claim,
                user.authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())
            )
            .sign(algorithm)

        val refreshToken = JWT.create()
            .withSubject(user.username)
            .withExpiresAt(Date(System.currentTimeMillis() + 5 * 60 * 1000))
            .withIssuer(request.requestURI)
            .withClaim(
                claim,
                user.authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())
            )
            .sign(algorithm)

        val tokens = mapOf("access_token" to accessToken, "refresh_token" to refreshToken)
        response.contentType = APPLICATION_JSON_VALUE
        ObjectMapper().writeValue(response.outputStream, tokens)

    }
}