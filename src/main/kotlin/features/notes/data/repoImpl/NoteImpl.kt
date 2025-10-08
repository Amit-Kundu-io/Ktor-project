package com.a.features.notes.data.repoImpl

import com.a.features.notes.data.models.Note
import com.a.features.notes.data.models.NoteRequest
import com.a.features.notes.data.table.NoteTable
import com.a.features.notes.domain.repository.NoteRepo
import com.a.features.notes.entity.NotesEntity
import com.a.utils.helper.dbQuery
import com.a.utils.helper.idGenerate
import org.jetbrains.exposed.sql.update

class NoteImpl : NoteRepo {
    override suspend fun createAndUpdateNote(request: NoteRequest): Note? {
        return dbQuery {
            if (request.noteId == null) {
                // Create a new note
                val note = NotesEntity.new {
                    noteId= idGenerate()
                    noteTitle = request.noteTitle
                    noteContains = request.noteContains
                    userId = request.userId
                }
                note.toNote()
            } else {
                // Update existing note
                NoteTable.update({ NoteTable.noteId eq request.noteId }) {
                    it[noteTitle] = request.noteTitle
                    it[noteContains] = request.noteContains
                }

                Note(
                    noteId = request.noteId,
                    noteTitle = request.noteTitle,
                    noteContains = request.noteContains,
                    userId = request.userId
                )
            }
        }
    }

    override suspend fun getAllNote(userId: String): List<Note?>? {
        return dbQuery {
          val notes =  NotesEntity.find { NoteTable.userId eq userId }
                .map { it.toNote() }
            return@dbQuery notes
        }
    }

}