package com.a.NotesApp.features.auth.service

import com.a.NotesApp.features.auth.models.RegisterRequest
import com.a.features.auth.data.models.User
import com.a.utils.helper.ApiResponse

interface AuthService {
    suspend fun createUser(request: RegisterRequest) : ApiResponse<User?>
}