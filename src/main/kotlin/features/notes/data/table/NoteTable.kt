package com.a.features.notes.data.table

import org.jetbrains.exposed.dao.id.IntIdTable

object NoteTable : IntIdTable("notes") {
    val userId = varchar("user_id", 50)
    val noteId = varchar("note_id", 50).uniqueIndex()
    val noteTitle = text("note_title")
    val noteContains = text("note_contains", )
}