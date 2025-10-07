package com.a.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest (
    val userName : String,
    val password : String
)
