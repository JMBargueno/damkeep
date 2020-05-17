package com.jmbargueno.damkeep.repository

import com.jmbargueno.damkeep.model.AppUser
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<AppUser, UUID> {

    fun findByUsername(username: String): Optional<AppUser>


}