package com.a.note_app.features.notes.data.repoImpl

import com.a.NotesApp.features.auth.tables.UserTable
import com.a.note_app.features.auth.entity.UserEntity
import com.a.note_app.features.notes.data.models.Note
import com.a.note_app.features.notes.data.models.NoteRequest
import com.a.note_app.features.notes.data.table.NoteTable
import com.a.note_app.features.notes.domain.repository.NoteRepo
import com.a.note_app.features.notes.entity.NotesEntity
import com.a.utils.helper.dbQuery
import com.a.utils.helper.idGenerate


class NoteImpl : NoteRepo {


    override suspend fun createAndUpdateNote(request: NoteRequest): Note? = dbQuery {
        val user = UserEntity.find { UserTable.id eq request.userId }.firstOrNull() ?: return@dbQuery null

        if (request.noteId.isNullOrBlank()) {
            // Create
            val newId = idGenerate()
            NotesEntity.new(newId) {
                userId = user.id.value
                noteTitle = request.noteTitle
                noteContains = request.noteContains
            }.toNote()
        } else {
            // Update
            val note = NotesEntity.findById(request.noteId) ?: return@dbQuery null
            note.noteTitle = request.noteTitle
            note.noteContains = request.noteContains
            note.toNote()
        }
    }


    override suspend fun getAllNote(userId: String): List<Note?>? = dbQuery {
        NotesEntity.find { NoteTable.userId eq userId }
            .limit(10)
            .map { it.toNote() }
    }


    override suspend fun deleteNote(noteId: String): Note? = dbQuery {
        val note = NotesEntity.findById(noteId) ?: return@dbQuery null
        val result = note.toNote()
        note.delete()
        result
    }


}