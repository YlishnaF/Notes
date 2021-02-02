package com.example.notes.ui.main

import com.example.notes.data.model.Note
import com.example.notes.ui.common.BaseViewState

class MainViewState (val notes: List<Note>?=null, error: Throwable?=null) :
    BaseViewState<List<Note>?>(notes, error)
