package com.jmbargueno.damkeepapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jmbargueno.damkeepapp.client.request.CreateNoteRequest
import com.jmbargueno.damkeepapp.model.Note
import com.jmbargueno.damkeepapp.repository.DamKeepAppRepository
import javax.inject.Inject

class NoteViewModel @Inject constructor(damKeepRepository: DamKeepAppRepository) : ViewModel() {
    val repository = damKeepRepository

    fun createNote(request: CreateNoteRequest): MutableLiveData<Note> {
        return repository.newNote(request)
    }

    fun removeNote(id: String) {
        return repository.removeNote(id)
    }

    fun editNote(id: String, request: CreateNoteRequest): MutableLiveData<Note> {
        return repository.modifyNote(id, request)
    }

    fun getNote(id: String): MutableLiveData<Note> {
        return repository.getNote(id)
    }

    fun getAll(): MutableLiveData<List<Note>> {
        return repository.getAllNotes()
    }


}