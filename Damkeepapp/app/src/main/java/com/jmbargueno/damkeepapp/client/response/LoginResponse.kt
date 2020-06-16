package com.jmbargueno.damkeepapp.client.response

import com.jmbargueno.damkeepapp.model.AppUser

data class LoginResponse (
    val token :String,
    val user : AppUser
)