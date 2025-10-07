package com.a.domain.services

import com.a.domain.models.LoginRequest
import com.a.domain.models.RegisterRequest
import com.a.domain.models.User

interface UserServices {
    suspend fun registerUser(request : RegisterRequest) : User?
    suspend fun loginUser(request: LoginRequest) : User?
    suspend fun getUserById(id : Int) : User?
}