package com.jmbargueno.damkeep.other

import com.jmbargueno.damkeep.dto.PostNote
import com.jmbargueno.damkeep.dto.UserSignUp
import com.jmbargueno.damkeep.dto.toAppUser
import com.jmbargueno.damkeep.dto.toNote
import com.jmbargueno.damkeep.model.AppUser
import com.jmbargueno.damkeep.model.Note
import com.jmbargueno.damkeep.repository.NoteRepository
import com.jmbargueno.damkeep.repository.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class InitDataComponent(val userRepository: UserRepository,
                        val noteRepository: NoteRepository,
                        val passwordEncoder: PasswordEncoder = BCryptPasswordEncoder()) {

    @PostConstruct
    fun initData() {
        var user: AppUser = UserSignUp("admin", passwordEncoder.encode("1234")).toAppUser()
        userRepository.save(user)

        var noteOne: Note = PostNote("Comprar gambas", "Necesito comprar un kilo gambas").toNote(user)
        noteRepository.save(noteOne)
        user.notes?.add(noteOne)

        var noteTwo: Note = PostNote("Hacer paella", "Hay que comprar los ingredientes para hacer la paella").toNote(user)
        noteRepository.save(noteTwo)
        user.notes?.add(noteTwo)

        var noteThree: Note = PostNote("Ir al Costco", "Hay que comprar vinito barato, que semossss pobres").toNote(user)
        noteRepository.save(noteThree)
        user.notes?.add(noteThree)

        userRepository.save(user)

    }
}