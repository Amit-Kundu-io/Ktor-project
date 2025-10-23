package com.a.note_app.features.notes.domain.repository

import com.a.note_app.features.notes.data.models.Note
import com.a.note_app.features.notes.data.models.NoteRequest


interface NoteRepo {
    suspend fun createAndUpdateNote(request: NoteRequest) : Note?
    suspend fun getAllNote(userId : String) : List<Note?>?
    suspend fun deleteNote(noteId : String) : Note?
}