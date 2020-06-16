package com.jmbargueno.damkeep.controller

import com.jmbargueno.damkeep.dto.GetUser
import com.jmbargueno.damkeep.dto.UserSignUp
import com.jmbargueno.damkeep.dto.toAppUser
import com.jmbargueno.damkeep.dto.toGetUser
import com.jmbargueno.damkeep.model.AppUser
import com.jmbargueno.damkeep.request.RequestLogin
import com.jmbargueno.damkeep.response.JwtUserResponse
import com.jmbargueno.damkeep.security.JwtTokenProvider
import com.jmbargueno.damkeep.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import javax.validation.Valid

@RestController
class AuthController(
        private val authenticationManager: AuthenticationManager,
        private val jwtTokenProvider: JwtTokenProvider,
        private val userService: UserService,
        private val bCryptPasswordEncoder: BCryptPasswordEncoder
) {


    @PostMapping("/signup")
    fun signup(@RequestBody user: UserSignUp): GetUser {
        val result = userService.findByUsername(user.username)
        if (result == null) throw ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre de usuario ${user.username} ya existe.")
        else {
            user.password = bCryptPasswordEncoder.encode(user.password)
            return userService.save(user.toAppUser()).toGetUser()
        }
    }

    @PostMapping("/auth/login")
    fun login(@Valid @RequestBody loginRequest: RequestLogin): JwtUserResponse {
        val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                        loginRequest.username, loginRequest.password
                )
        )
        SecurityContextHolder.getContext().authentication = authentication
        val user = authentication.principal as AppUser
        val jwtToken = jwtTokenProvider.generateToken(authentication)
        println("ENTRA A LOGIN")
        return JwtUserResponse(jwtToken, user.toGetUser())
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user/me")
    fun me(@AuthenticationPrincipal user: AppUser) = user.toGetUser()
}

