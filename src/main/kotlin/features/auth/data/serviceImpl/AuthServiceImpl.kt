package com.a.features.auth.data.serviceImpl

import com.a.NotesApp.features.auth.models.RegisterRequest
import com.a.NotesApp.features.auth.repository.AuthRepo
import com.a.NotesApp.features.auth.service.AuthService
import com.a.features.auth.data.models.LoginRequest
import com.a.features.auth.data.models.User
import com.a.utils.helper.ApiResponse

class AuthServiceImpl(
    private val authRepo: AuthRepo
) : AuthService {
    override suspend fun createUser(request: RegisterRequest): ApiResponse<User?> {
        val result = authRepo.createUser(request)
        return try {
            if (result == null){
                ApiResponse(
                    message = listOf("User with this phone number already exists"),
                    succeeded = false,
                    totalItems = 0,
                    type = "User",
                    data = null,
                    statusCode = 409
                )
            }else{
                ApiResponse(
                    message = listOf("User created successfully"),
                    succeeded = true,
                    totalItems = 1,
                    type = "User",
                    data = result,
                    statusCode = 201
                )
            }
        }
        catch (e: Exception){
            ApiResponse(
                message = listOf("Failed to create user"),
                succeeded = false,
                totalItems = 0,
                type = "User",
                data = null,
                statusCode = 500
            )
        }
    }

    override suspend fun loginUser(request: LoginRequest): ApiResponse<User?> {
        val result = authRepo.loginUser(request)
       return try {
            if (result?.second == null){
                ApiResponse(
                    message = listOf(result?.first?: ""),
                    succeeded = false,
                    totalItems = 0,
                    type = "User",
                    data = null,
                    statusCode = 401
                )
            }
           else{
                ApiResponse(
                    message = listOf("Password match"),
                    succeeded = true,
                    totalItems = 0,
                    type = "User",
                    data = result.second,
                    statusCode =  200
                )
            }
        }
        catch (e : Exception){
            ApiResponse(
                message = listOf("Failed to get user"),
                succeeded = false,
                totalItems = 0,
                type = "User",
                data = null,
                statusCode = 500
            )
        }
    }
}
