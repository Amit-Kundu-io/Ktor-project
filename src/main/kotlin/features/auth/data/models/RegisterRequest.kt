package com.a.NotesApp.features.auth.models

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val userName: String,
    val phNumber : String,
    val password: String
)
