package com.jmbargueno.damkeep.dto

import com.jmbargueno.damkeep.model.AppUser
import java.util.*

data class GetUser(
        val id: UUID?,
        var username: String,
        var rol: String
);

fun AppUser.toGetUser() = GetUser (
        id, username, if (roles.size == 1) roles.first() else roles.elementAt(1)
);

data class UserSignUp(
        var username: String,
        var password: String
);

fun UserSignUp.toAppUser() = AppUser(
        username, password, ArrayList(), "USER"
);