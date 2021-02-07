package com.example.notes.data

import android.util.Log
import com.example.notes.data.model.FireStoreProvider
import com.example.notes.data.model.Note
import com.example.notes.data.model.RemoteDataProvider
import com.example.notes.extensions.MyLog

object Repository {
    private val remoteDataProvider: RemoteDataProvider = FireStoreProvider()

    fun getNotes() = remoteDataProvider.subscribeToAllNotes()

    fun saveNote(note: Note) = remoteDataProvider.saveNote(note)

    fun getNoteById(id: String) = remoteDataProvider.getNoteById(id)
}