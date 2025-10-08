package com.a.features.auth.data.models

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val phoneNumber : String,
    val password : String
)
