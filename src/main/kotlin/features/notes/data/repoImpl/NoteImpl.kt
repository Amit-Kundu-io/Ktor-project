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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import kotlin.system.measureTimeMillis
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction


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


    override suspend fun getAllNote(userId: String): List<Note> = dbQuery {
        NoteTable
            .select(NoteTable.userId eq userId)
            .limit(10)
            .map {
                Note(
                    noteId = it[NoteTable.id].value,
                    noteTitle = it[NoteTable.noteTitle],
                    noteContains = it[NoteTable.noteContains],
                    userId = it[NoteTable.userId]
                )
            }
    }


    override suspend fun deleteNote(noteId: String): Note? = dbQuery {
        val note = NotesEntity.findById(noteId) ?: return@dbQuery null
        val result = note.toNote()
        note.delete()
        result
    }


}