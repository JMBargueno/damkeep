package com.jmbargueno.damkeep.response

import com.jmbargueno.damkeep.dto.GetUser

data class JwtUserResponse(
        val token: String,
        val user: GetUser
)