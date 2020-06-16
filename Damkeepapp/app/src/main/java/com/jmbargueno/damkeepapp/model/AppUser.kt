package com.jmbargueno.damkeepapp.model

import java.util.*

data class AppUser(
    val id : UUID,
    val username : String,
    val rol: String
)