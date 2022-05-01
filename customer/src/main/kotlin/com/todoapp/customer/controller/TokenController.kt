package com.todoapp.customer.controller

import com.todoapp.customer.service.TokenService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller("/token")
class TokenController(
    private val tokenService: TokenService
) {

    @GetMapping("/refresh")
    fun refreshToken(request: HttpServletRequest, response: HttpServletResponse) {
        tokenService.refreshToken(request, response)
    }

}