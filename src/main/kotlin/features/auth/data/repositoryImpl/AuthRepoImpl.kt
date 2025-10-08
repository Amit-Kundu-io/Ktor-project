package com.a.NotesApp.features.auth.repositoryImpl


import com.a.NotesApp.features.auth.models.RegisterRequest
import com.a.NotesApp.features.auth.repository.AuthRepo
import com.a.NotesApp.features.auth.tables.UserTable
import com.a.features.auth.data.models.LoginRequest
import com.a.features.auth.data.models.User
import com.a.features.auth.entity.UserEntity
import com.a.utils.helper.PasswordHasher
import com.a.utils.helper.dbQuery
import com.a.utils.helper.userIdGenerate


class AuthRepoImpl : AuthRepo {
    override suspend fun createUser(request: RegisterRequest): User? {
        return dbQuery {
            val user = UserEntity.find { UserTable.phNumber eq request.phoneNumber }.firstOrNull()
            if (user != null) {
                return@dbQuery user.toUser()
            }
            val newUser = UserEntity.new {
                this.userName = request.userName
                this.userId = userIdGenerate()
                this.password =  PasswordHasher.hash(request.password)
                this.phNumber =request.phoneNumber
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

    override suspend fun loginUser(request: LoginRequest): Pair<String?, User?>? {
        return dbQuery {
            val user = UserEntity.find { UserTable.phNumber eq request.phoneNumber }.firstOrNull()

            if (user == null) {
                // User not found
                return@dbQuery Pair("User not found", null)
            }

            if (PasswordHasher.verify(request.password, user.password)) {
                // Password correct
                return@dbQuery Pair(null, user.toUser())
            } else {
                // Password incorrect
                return@dbQuery Pair("Password mismatch", null)
            }
        }
    }
}