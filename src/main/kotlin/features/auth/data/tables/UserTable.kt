package com.a.NotesApp.features.auth.tables

import com.a.features.auth.data.models.User
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.ResultRow


object UserTable : IdTable<String>("users") {
    override val id = varchar("user_id", 50).entityId().uniqueIndex() // string primary key
    val userName = varchar("user_name", 50)
    val phNumber = varchar("ph_number", 20).index()
    val password = text("user_password")


    fun ResultRow.toUser() = User(
        userId = this[id].value,
        userName = this[userName],
        phoneNumber = this[phNumber]
    )


}

