package com.a.domain.repo

import com.a.domain.models.LoginRequest
import com.a.domain.models.RegisterRequest
import com.a.domain.models.User

interface UserRepo {
    suspend fun createUser(request: RegisterRequest): User?
    suspend fun getUserById(id: Int): User?
    suspend fun loginUser(request: LoginRequest): User?
    fun verifyPassword(plainPass: String, hashPass: String): Boolean
    fun hashPassword(password: String): String
}