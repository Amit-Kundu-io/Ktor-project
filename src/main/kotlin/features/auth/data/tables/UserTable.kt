package com.a.NotesApp.features.auth.tables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table



object UserTable : IntIdTable("users") {
    val userId = varchar("user_id", 50).uniqueIndex()
    val userName = varchar("user_name", 50)
    val phNumber = varchar("ph_number", 20)
    val password = text("user_password")
}

