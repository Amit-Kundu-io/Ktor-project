package com.a.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id : Int,
    val user_name : String
)
