package com.a.features.notes.domain.repository

import com.a.features.notes.data.models.Note
import com.a.features.notes.data.models.NoteRequest

interface NoteRepo {
    suspend fun createAndUpdateNote(request: NoteRequest) : Note?
    suspend fun getAllNote(userId : String) : List<Note?>?
}