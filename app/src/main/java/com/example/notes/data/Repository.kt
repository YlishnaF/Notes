package com.example.notes.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notes.data.model.Color
import com.example.notes.data.model.FireStoreProvider
import com.example.notes.data.model.Note
import com.example.notes.data.model.RemoteDataProvider
import java.util.*

object Repository {
    private val remoteDataProvider : RemoteDataProvider = FireStoreProvider()

    fun getNotes() = remoteDataProvider.subscribeToAllNotes()

    fun saveNote(note: Note) = remoteDataProvider.saveNote(note)

    fun getNoteById(id:String) = remoteDataProvider.getNoteById(id)


}