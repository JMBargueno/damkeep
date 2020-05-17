package com.jmbargueno.damkeep.controller

import com.jmbargueno.damkeep.dto.GetNote
import com.jmbargueno.damkeep.dto.PostNote
import com.jmbargueno.damkeep.dto.toGetNote
import com.jmbargueno.damkeep.dto.toNote
import com.jmbargueno.damkeep.model.AppUser
import com.jmbargueno.damkeep.model.Note
import com.jmbargueno.damkeep.service.NoteService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*


@RestController
@RequestMapping("/notes")
class NoteController(val noteService: NoteService) {

    @PostMapping("/")
    fun postNote(@RequestBody note: PostNote, @AuthenticationPrincipal user: AppUser): ResponseEntity<*> {
        val result = noteService.save(note.toNote(user))
        if (result != null) return ResponseEntity<GetNote>(result.toGetNote(), HttpStatus.CREATED)
        else throw ResponseStatusException(
                HttpStatus.BAD_REQUEST, "${note.title} no se ha podido crear con éxito")
    }

    @GetMapping("/{id}")
    fun getNote(@PathVariable id: UUID): GetNote {
        var result = noteService.findById(id)
        if (result.isPresent) return result.get().toGetNote()
        else throw ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado la nota con id: $id")
    }

    @GetMapping("/")
    fun getNotesByUser(@AuthenticationPrincipal user: AppUser): List<GetNote> {
        val result: List<Note> = noteService.findByUser(user)
        if (result.isNotEmpty()) return result.map { it.toGetNote() }
        else throw ResponseStatusException(HttpStatus.NO_CONTENT, "Sin notas")
    }

    @PutMapping("/{id}")
    fun editNote(@PathVariable id: UUID, @RequestBody note: PostNote): GetNote {
        return noteService.findById(id)
                .map { x ->
                    val edited: Note = x.copy(
                            title = note.title, content = note.content
                    )
                    noteService.save(edited).toGetNote()
                }.orElseThrow {
                    ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado la nota con id: $id")
                }
    }

    @DeleteMapping("/{id}")
    fun deleteNote(@PathVariable id: UUID): ResponseEntity<Void> {
        var result = noteService.findById(id)
        if (result.isPresent) {
            noteService.deleteById(id)
            return ResponseEntity.noContent().build()
        } else throw ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado la nota con id: $id")


    }
}