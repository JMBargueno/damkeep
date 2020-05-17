package com.jmbargueno.damkeep.service

import com.jmbargueno.damkeep.model.AppUser
import com.jmbargueno.damkeep.model.Note
import com.jmbargueno.damkeep.repository.NoteRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
open class NoteService : BaseService<Note, UUID, NoteRepository>() {

    fun findByUser(user: AppUser): List<Note> {
        return this.repository.findAllByAuthor(user)
    }
}