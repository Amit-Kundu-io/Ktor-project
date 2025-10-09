package com.a.NotesApp.features.auth.repositoryImpl


import com.a.NotesApp.features.auth.models.RegisterRequest
import com.a.NotesApp.features.auth.repository.AuthRepo
import com.a.NotesApp.features.auth.tables.UserTable
import com.a.NotesApp.features.auth.tables.UserTable.toUser
import com.a.features.auth.data.models.LoginRequest
import com.a.features.auth.data.models.User
import com.a.features.auth.entity.UserEntity
import com.a.utils.helper.PasswordHasher
import com.a.utils.helper.dbQuery
import com.a.utils.helper.idGenerate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import kotlin.system.measureTimeMillis


class AuthRepoImpl : AuthRepo {

    override suspend fun createUser(request: RegisterRequest): User? {
        // Hash password outside transaction for performance
        val hashedPassword = withContext(Dispatchers.Default) {
            PasswordHasher.hash(request.password)
        }

        return dbQuery {
            // Check if user already exists
            val existingUser = UserEntity.find { UserTable.phNumber eq request.phoneNumber }.firstOrNull()
            if (existingUser != null) return@dbQuery null

            // Create new user using entity (preferred over raw insert)
            val newUser = UserEntity.new {
                userId =idGenerate()
                userName = request.userName
                phNumber = request.phoneNumber
                password = hashedPassword
            }

            newUser.toUser()
        }
    }

    override suspend fun loginUser(request: LoginRequest): Pair<String?, User?>? {
        return dbQuery {
            val user = UserEntity.find { UserTable.phNumber eq request.phoneNumber }.firstOrNull()
                ?: return@dbQuery "User not found" to null

              val isValid = PasswordHasher.verify(request.password, user.password)

            if (isValid) null to user.toUser()
            else "Password mismatch" to null
        }
    }
}
