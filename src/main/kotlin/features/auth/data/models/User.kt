package com.a.features.auth.data.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id : Int?,
    val userId : String?,
    val userName : String?,
    val phoneNumber : String?,
)
