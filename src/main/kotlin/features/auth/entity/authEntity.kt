package com.a.features.auth.entity

import com.a.NotesApp.features.auth.tables.UserTable
import com.a.features.auth.data.models.User
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

class UserEntity(id : EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UserEntity>(UserTable)
    var userId by UserTable.userId
    var userName by UserTable.userName
    var password by UserTable.password
    var phNumber by UserTable.phNumber

    fun toUser () = User(
        id = id.value,
        userId = userId,
        userName = userName,
        phoneNumber = phNumber
    )

}