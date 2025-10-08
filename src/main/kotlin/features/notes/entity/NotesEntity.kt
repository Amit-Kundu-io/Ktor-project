package com.a.features.notes.entity

import com.a.features.notes.data.models.Note
import com.a.features.notes.data.table.NoteTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class NotesEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<NotesEntity>(NoteTable)

    var userId by NoteTable.userId
    var noteId by NoteTable.noteId
    var noteTitle by NoteTable.noteTitle
    var noteContains by NoteTable.noteContains


    fun toNote() = Note(
        noteId = noteId,
        userId = userId,
        noteContains = noteContains,
        noteTitle = noteTitle
    )

}