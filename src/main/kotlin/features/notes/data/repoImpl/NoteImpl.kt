package com.a.features.notes.data.repoImpl

import com.a.NotesApp.features.auth.tables.UserTable
import com.a.features.auth.entity.UserEntity
import com.a.features.notes.data.models.Note
import com.a.features.notes.data.models.NoteRequest
import com.a.features.notes.data.table.NoteTable
import com.a.features.notes.domain.repository.NoteRepo
import com.a.features.notes.entity.NotesEntity
import com.a.utils.helper.dbQuery
import com.a.utils.helper.idGenerate
import jdk.jfr.internal.JVM.log
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import kotlin.system.measureTimeMillis

class NoteImpl : NoteRepo {


    override suspend fun createAndUpdateNote(request: NoteRequest): Note? {
        var result: Note? = null
        val time = measureTimeMillis {
            result = dbQuery {
                UserEntity.find { UserTable.userId eq request.userId }.firstOrNull() ?: return@dbQuery null

                if (request.noteId.isNullOrBlank()) {
                    // Create
                    val newId = idGenerate()
                    NotesEntity.new(newId) {
                        userId = request.userId
                        noteTitle = request.noteTitle
                        noteContains = request.noteContains
                    }.toNote()
                } else {
                    // Update
                    NoteTable.update({ NoteTable.id eq request.noteId }) {
                        it[noteTitle] = request.noteTitle
                        it[noteContains] = request.noteContains
                    }
                    NotesEntity.findById(request.noteId)?.toNote()
                }
            }
        }
        println("createAndUpdateNote executed in $time ms")
        return result
    }



    override suspend fun getAllNote(userId: String): List<Note?>? {
        return dbQuery {
            //var result: List<Note?>? = null

            val rawNotes = NotesEntity.find { NoteTable.userId eq userId }
            val result = mutableListOf<Note?>()
            rawNotes.forEachIndexed { i, entity ->
                 result.add(entity.toNote())
            }

            return@dbQuery result
        }
    }


    override suspend fun deleteNote(noteId: String): Note? {
        return dbQuery {
            val note = NotesEntity.find { NoteTable.id eq noteId }
                .firstOrNull()
                ?.toNote()

            if (note != null) {
                NoteTable.deleteWhere { NoteTable.id eq noteId }
                note
            } else {
                null
            }
        }
    }

}