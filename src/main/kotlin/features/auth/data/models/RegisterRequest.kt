package com.a.NotesApp.features.auth.models

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val userName: String,
    val phoneNumber : String,
    val password: String
)
