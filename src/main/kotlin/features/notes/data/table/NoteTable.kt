package com.a.features.notes.data.table

import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.dao.id.IntIdTable

object NoteTable : IdTable<String>("notes") {
    override val id = varchar("note_id", 50).entityId().uniqueIndex() // string primary key
    val userId = varchar("user_id", 50).index()
    val noteTitle = text("note_title")
    val noteContains = text("note_contains")
}

