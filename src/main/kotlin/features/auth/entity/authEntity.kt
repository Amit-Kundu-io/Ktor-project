package com.a.features.auth.entity

import com.a.NotesApp.features.auth.tables.UserTable
import com.a.features.auth.data.models.User
import com.a.features.notes.data.table.NoteTable
import com.a.features.notes.entity.NotesEntity
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

class UserEntity(id : EntityID<String>) : Entity<String>(id) {
    companion object : EntityClass<String, UserEntity>(UserTable)
    var userName by UserTable.userName
    var password by UserTable.password
    var phNumber by UserTable.phNumber

    fun toUser () = User(
        userId = id.value,
        userName = userName,
        phoneNumber = phNumber
    )

}