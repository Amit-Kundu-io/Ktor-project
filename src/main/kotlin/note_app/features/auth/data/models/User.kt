package com.a.note_app.features.auth.data.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val userId : String? = null,
    val userName : String?,
    val phoneNumber : String?,
)
