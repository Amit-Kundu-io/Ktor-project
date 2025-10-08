package com.a.features.notes.domain.service

import com.a.features.notes.data.models.Note
import com.a.features.notes.data.models.NoteRequest
import com.a.utils.helper.ApiResponse

interface NoteServices {
    suspend fun createAndUpdateNote(request: NoteRequest) : ApiResponse<Note?>
    suspend fun getAllNotes(userId: String?) : ApiResponse<List<Note?>?>


}