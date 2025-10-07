package com.a.data.repoImpl

import com.a.db.Utils.dbQuery
import com.a.db.entities.UserEntity
import com.a.db.table.UserTable
import com.a.domain.models.LoginRequest
import com.a.domain.models.RegisterRequest
import com.a.domain.models.User
import com.a.domain.repo.UserRepo
import org.mindrot.jbcrypt.BCrypt

class UserRepoImpl : UserRepo {
    override suspend fun createUser(request: RegisterRequest): User? {
        return dbQuery {
            val user = UserEntity.find { UserTable.user_name eq request.userName}.firstOrNull()
                if (user != null) {
                    return@dbQuery user.toUser()
                }
            val newUser = UserEntity.new {
                this.userName = request.userName
                this.password = hashPassword( request.password)
            }.let {
                User(it.userName.toIntOrNull() ?: 0,it.password)
            }

            return@dbQuery newUser
        }
    }

    override suspend fun getUserById(id: Int): User? {
       return dbQuery {
           UserEntity.findById(id)?.toUser()
       }
    }

    override suspend fun loginUser(request: LoginRequest): User? {
        return dbQuery {
            val user = UserEntity.find { UserTable.user_name eq request.userName }.firstOrNull()
            user?.takeIf {
                verifyPassword(request.password,user.password)}
                ?.let {
                    User(
                        user.id.value , user.userName
                    )
                }
            }
    }

    override fun verifyPassword(plainPass: String, hashPass: String): Boolean {
        return BCrypt.checkpw(plainPass , hashPass)
    }

    override  fun hashPassword(password: String): String {
        return BCrypt.hashpw(password, BCrypt.gensalt())

    }
}