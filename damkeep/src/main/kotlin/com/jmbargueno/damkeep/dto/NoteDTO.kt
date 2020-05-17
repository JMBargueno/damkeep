package com.jmbargueno.damkeep.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.jmbargueno.damkeep.model.AppUser
import com.jmbargueno.damkeep.model.Note
import org.springframework.web.servlet.mvc.LastModified
import java.time.LocalDateTime
import java.util.*

data class GetNote(
        val id: UUID? = null,
        var title: String,
        var content: String,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
        var creationDate: LocalDateTime,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
        var lastModified: LocalDateTime,
        var author: GetUser? = null
);

fun Note.toGetNote() = GetNote(
        id,
        title,
        content,
        creationDate = this.creationDate!!,
        lastModified = this.lastModified!!,
        author = author?.toGetUser()
);

data class PostNote(
        var title: String,
        var content: String
);

fun PostNote.toNote(user: AppUser) = Note(
        title,
        content,
        author = user
);
