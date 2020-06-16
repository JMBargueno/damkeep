package com.jmbargueno.damkeepapp.client.response

import java.util.*

data class SignupResponse(
    val id: UUID,
    val username: String,
    val rol: String
)