package com.a.db.entities

import com.a.db.table.UserTable
import com.a.domain.models.User
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class UserEntity(id : EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UserEntity>(UserTable)
    var userName by UserTable.user_name
    var password by UserTable.password

    fun toUser () = User(
        id = id.value,
        user_name = userName,
    )
}