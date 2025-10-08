package com.a.NotesApp.features.auth.repositoryImpl


import com.a.NotesApp.features.auth.models.RegisterRequest
import com.a.NotesApp.features.auth.repository.AuthRepo
import com.a.NotesApp.features.auth.tables.UserTable
import com.a.features.auth.data.models.User
import com.a.features.auth.entity.UserEntity
import com.a.utils.helper.PasswordHasher
import com.a.utils.helper.dbQuery
import com.a.utils.helper.userIdGenerate
import org.jetbrains.exposed.sql.insert


class AuthRepoImpl : AuthRepo {
    override suspend fun createUser(request: RegisterRequest): User? {
        return dbQuery {
            val user = UserEntity.find { UserTable.phNumber eq request.phNumber }.firstOrNull()
            if (user != null) {
                return@dbQuery user.toUser()
            }
            val newUser = UserEntity.new {
                this.userName = request.userName
                this.userId = userIdGenerate()
                this.password =  PasswordHasher.hash(request.password)
                this.phNumber =request.phNumber
            }.let {

                User(
                    id = it.id.value,
                    userId = it.userId,
                    userName = it.userName,
                    phoneNumber = it.phNumber,
                )
            }
            return@dbQuery newUser
        }
    }
}