package com.a.data.services

import com.a.domain.models.LoginRequest
import com.a.domain.models.RegisterRequest
import com.a.domain.models.User
import com.a.domain.repo.UserRepo
import com.a.domain.services.UserServices

class UserServicesImpl (
    private val userRepo: UserRepo
): UserServices {
    override suspend fun registerUser(request: RegisterRequest): User? {
        return userRepo.createUser(request)
    }

    override suspend fun loginUser(request: LoginRequest): User? {
        return userRepo.loginUser(request)
    }

    override suspend fun getUserById(id: Int): User? {
        return userRepo.getUserById(id)
    }
}