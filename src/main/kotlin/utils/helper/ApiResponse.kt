package com.a.utils.helper

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    val message: List<String>,
    val succeeded: Boolean,
    val totalItems: Int? = null,
    val type: String? = null,
    val data: T? = null,
    val statusCode: Int
)