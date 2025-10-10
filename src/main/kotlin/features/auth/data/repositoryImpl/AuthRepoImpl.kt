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
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import kotlin.system.measureTimeMillis


class AuthRepoImpl : AuthRepo {
/*
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
            val newUser = UserEntity.new(idGenerate()) {
                userName = request.userName
                phNumber = request.phoneNumber
                password = hashedPassword
            }

            newUser.toUser()
        }
    }


 */
override suspend fun createUser(request: RegisterRequest): User? {
    val totalStart = System.currentTimeMillis()

    //Hash password outside transaction
    val hashStart = System.currentTimeMillis()
    val hashedPassword = withContext(Dispatchers.Default) {
        PasswordHasher.hash(request.password)
    }
    println("🔐 Password hashing took: ${System.currentTimeMillis() - hashStart} ms")

    // Run DB transaction
    val dbStart = System.currentTimeMillis()
    val result = dbQuery {
        // Fast existence check using raw SQL
        val exists = UserTable
            .select ( UserTable.phNumber eq request.phoneNumber )
            .limit(1)
            .empty().not()

        if (exists) return@dbQuery null

        // Insert user using raw SQL for performance
        UserTable.insert {
            it[id] = idGenerate()
            it[userName] = request.userName
            it[phNumber] = request.phoneNumber
            it[password] = hashedPassword
        }

        //  Retrieve and map the inserted user
        val newUserEntity = UserEntity.find {
            UserTable.phNumber eq request.phoneNumber
        }.firstOrNull()

        newUserEntity?.toUser()
    }
    println("🗄️ DB transaction took: ${System.currentTimeMillis() - dbStart} ms")
    println("⏱️ Total createUser time: ${System.currentTimeMillis() - totalStart} ms")

    return result
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

    override suspend fun simulateDelayTask(request: String): String? {
        delay(5000) // Wait for 5 seconds
        val output = "✅ Task completed for input: $request"
        return output
    }
}
