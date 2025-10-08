package com.a.features.notes.data.serviceImpl

import com.a.features.notes.data.models.Note
import com.a.features.notes.data.models.NoteRequest
import com.a.features.notes.domain.repository.NoteRepo
import com.a.features.notes.domain.service.NoteServices
import com.a.utils.helper.ApiResponse

class NoteServiceImpl(
    private val noteRepo: NoteRepo
) : NoteServices {
    override suspend fun createAndUpdateNote(request: NoteRequest): ApiResponse<Note?> {
        val result = noteRepo.createAndUpdateNote(request)

        print(result)

        return try {

            if (result == null) {
                ApiResponse(
                    message = listOf("Failed to create and update note"),
                    succeeded = false,
                    totalItems = 0,
                    type = "Note",
                    data = null,
                    statusCode = 400
                )
            } else {
                ApiResponse(
                    message = listOf("Successfully create or update"),
                    succeeded = true,
                    totalItems = 0,
                    type = "Note",
                    data = result,
                    statusCode = 200
                )
            }

        } catch (e: Exception) {
            print("Failed to create and update note $result")
            ApiResponse(
                message = listOf("Failed to create and update note $e"),
                succeeded = false,
                totalItems = 0,
                type = "Note",
                data = null,
                statusCode = 500
            )
        }
    }

    override suspend fun getAllNotes(userId: String?): ApiResponse<List<Note?>?> {

        return try {
            if (userId.isNullOrBlank()) {
                ApiResponse(
                    message = listOf("Id not null or blank"),
                    succeeded = true,
                    totalItems = 0,
                    type = "Notes",
                    data = null,
                    statusCode = 404
                )
            }

            val result = userId?.let { noteRepo.getAllNote(it) }
            if (result == null) {
                ApiResponse(
                    message = listOf("Note not found"),
                    succeeded = false,
                    totalItems = 0,
                    type = "Notes",
                    data = null,
                    statusCode = 404
                )
            } else {
                ApiResponse(
                    message = listOf("Note found"),
                    succeeded = true,
                    totalItems = result.size,
                    type = "Notes",
                    data = result,
                    statusCode = 200
                )
            }

        } catch (e: Exception) {
            ApiResponse(
                message = listOf("Failed to get note"),
                succeeded = false,
                totalItems = 0,
                type = "Notes",
                data = null,
                statusCode = 500
            )
        }
    }

}