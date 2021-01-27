package com.example.notes.ui.note

import androidx.lifecycle.ViewModel
import com.example.notes.data.Repository
import com.example.notes.data.model.Note
import java.util.*

class NoteViewModel(private val repository: Repository = Repository) : ViewModel() {

    private var pendingNote: Note? = null

    fun saveChanges(note: Note) {
        pendingNote = note
    }

    override fun onCleared() {
        if (pendingNote != null) {
            repository.saveNotes(pendingNote!!)
        }
    }

    fun newID(): String =
        UUID.randomUUID().toString()
}
