package com.todoapp.customer.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.databind.ObjectMapper
import com.todoapp.customer.security.CloudConfigProperties.claim
import com.todoapp.customer.security.CloudConfigProperties.prefix
import com.todoapp.customer.security.CloudConfigProperties.salt
import mu.KotlinLogging
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomAuthorizationFilter : OncePerRequestFilter() {

    private val logger = KotlinLogging.logger {}

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (request.servletPath.equals("/customer/login") ||
            request.servletPath.equals("/customer/register") ||
            request.servletPath.equals("/customer/confirm")
        ) {
            filterChain.doFilter(request, response)
        } else {
            val header = request.getHeader(AUTHORIZATION)
            if (header != null && header.startsWith(prefix)) {
                try {
                    val jwtToken = header.substringAfter(prefix)
                    val algorithm = Algorithm.HMAC256(salt.toByteArray())

                    val verifier = JWT.require(algorithm).build()
                    val decodedJWT = verifier.verify(jwtToken)

                    val username = decodedJWT.subject
                    val roles = decodedJWT.getClaim(claim).asArray(String::class.java)
                    val authorities = roles.map { role -> SimpleGrantedAuthority(role) }

                    val tokenAuthentication =
                        UsernamePasswordAuthenticationToken(username, null, authorities)

                    SecurityContextHolder.getContext().authentication = tokenAuthentication
                    filterChain.doFilter(request, response)
                } catch (e: Exception) {
                    logger.error("Error login in: ${e.message}")
                    response.apply {
                        status = HttpStatus.FORBIDDEN.value()
                        contentType = MediaType.APPLICATION_JSON_VALUE
                    }
                    ObjectMapper().writeValue(response.outputStream, mapOf("error_message" to e.message))
                }
            } else {
                filterChain.doFilter(request, response)
            }
        }
    }
}