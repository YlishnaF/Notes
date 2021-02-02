package com.example.notes.ui.note

import com.example.notes.data.model.Note
import com.example.notes.ui.common.BaseViewState

class NoteViewState(note: Note? = null, error: Throwable? = null) : BaseViewState<Note?>(note, error)