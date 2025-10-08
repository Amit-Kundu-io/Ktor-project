package com.a.features.notes.data.models

import kotlinx.serialization.Serializable

@Serializable
data class NoteRequest(
    val userId: String,
    val noteId: String? = null,
    val noteContains: String,
    val noteTitle: String,
)
