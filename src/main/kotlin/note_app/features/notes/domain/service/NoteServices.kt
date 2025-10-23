package com.a.note_app.features.notes.domain.service


import com.a.note_app.features.notes.data.models.Note
import com.a.note_app.features.notes.data.models.NoteRequest
import com.a.utils.helper.ApiResponse

interface NoteServices {
    suspend fun createAndUpdateNote(request: NoteRequest) : ApiResponse<Note?>
    suspend fun getAllNotes(userId: String?) : ApiResponse<List<Note?>?>
    suspend fun deleteNote(noteId : String?) : ApiResponse<Note?>


}