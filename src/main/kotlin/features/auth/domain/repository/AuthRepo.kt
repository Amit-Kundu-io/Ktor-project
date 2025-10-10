package com.a.NotesApp.features.auth.repository

import com.a.NotesApp.features.auth.models.RegisterRequest
import com.a.features.auth.data.models.LoginRequest
import com.a.features.auth.data.models.User

interface AuthRepo {
    suspend fun createUser(request : RegisterRequest) : User?
    suspend fun loginUser(request : LoginRequest) : Pair<String?,User?>?
    suspend fun simulateDelayTask(request : String) : String?
}