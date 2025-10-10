package com.a.features.notes.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Note (
    val noteId : String?,
    val userId : String?,
    val noteContains : String?,
    val noteTitle : String?,
)