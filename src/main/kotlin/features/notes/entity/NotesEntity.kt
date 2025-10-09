package com.a.features.notes.entity

import com.a.features.notes.data.models.Note
import com.a.features.notes.data.table.NoteTable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class NotesEntity(id: EntityID<String>) : Entity<String>(id) {
    companion object : EntityClass<String, NotesEntity>(NoteTable)

    var userId by NoteTable.userId
    var noteTitle by NoteTable.noteTitle
    var noteContains by NoteTable.noteContains

    fun toNote() = Note(
        noteId = id.value,
        userId = userId,
        noteTitle = noteTitle,
        noteContains = noteContains
    )
}
