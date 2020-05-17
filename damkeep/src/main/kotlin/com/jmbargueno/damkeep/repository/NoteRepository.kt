package com.jmbargueno.damkeep.repository

import com.jmbargueno.damkeep.model.AppUser
import com.jmbargueno.damkeep.model.Note
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface NoteRepository : JpaRepository<Note, UUID> {

    fun findAllByAuthor(author: AppUser): List<Note>
}