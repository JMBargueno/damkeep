package com.jmbargueno.damkeep.service

import com.jmbargueno.damkeep.dto.UserSignUp
import com.jmbargueno.damkeep.dto.toAppUser
import com.jmbargueno.damkeep.model.AppUser
import com.jmbargueno.damkeep.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(private val encoder: PasswordEncoder) : BaseService<AppUser, UUID, UserRepository>() {
    fun findByUsername(username: String) = this.repository.findByUsername(username)

    fun create(user: UserSignUp): Optional<AppUser> {
        if (findByUsername(user.username).isPresent) return Optional.empty()
        return Optional.of(
                this.save(user.toAppUser())
        )
    }
}