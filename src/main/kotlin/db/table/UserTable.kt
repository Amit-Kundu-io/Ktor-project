package com.a.db.table

import org.jetbrains.exposed.dao.id.IntIdTable

object UserTable : IntIdTable() {
    val user_id = integer("user_id").uniqueIndex()
    val user_name = varchar("user_name", 50)
    val password = varchar("user_password", 50)
}