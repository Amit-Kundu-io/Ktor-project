package com.a.features.notes.data.serviceImpl

import com.a.features.notes.data.models.Note
import com.a.features.notes.data.models.NoteRequest
import com.a.features.notes.domain.repository.NoteRepo
import com.a.features.notes.domain.service.NoteServices
import com.a.utils.helper.ApiResponse
import io.ktor.http.HttpStatusCode

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
                    data = null,
                    statusCode = HttpStatusCode.BadRequest.value
                )
            } else {
                ApiResponse(
                    message = listOf("Successfully create or update"),
                    succeeded = true,
                    totalItems = 0,
                    data = result,
                    statusCode =  HttpStatusCode.OK.value
                )
            }

        } catch (e: Exception) {
            print("Failed to create and update note $result")
            ApiResponse(
                message = listOf("Failed to create and update note $e"),
                succeeded = false,
                totalItems = 0,
                data = null,
                statusCode =  HttpStatusCode.InternalServerError.value
            )
        }
    }

    override suspend fun getAllNotes(userId: String?): ApiResponse<List<Note?>?> {

        return try {
            if (userId.isNullOrBlank()) {
               return ApiResponse(
                    message = listOf("Id not null or blank"),
                    succeeded = true,
                    totalItems = 0,
                    data = null,
                    statusCode =  HttpStatusCode.NotFound.value
                )
            }

            val result = userId.let { noteRepo.getAllNote(it) }
            if (result == null) {
                ApiResponse(
                    message = listOf("Note not found"),
                    succeeded = false,
                    totalItems = 0,
                    data = null,
                    statusCode = HttpStatusCode.BadRequest.value
                )
            } else {
                ApiResponse(
                    message = listOf("Note found"),
                    succeeded = true,
                    totalItems = result.size,
                    data = result,
                    statusCode = HttpStatusCode.OK.value
                )
            }

        } catch (e: Exception) {
            ApiResponse(
                message = listOf("Failed to get note"),
                succeeded = false,
                totalItems = 0,
                data = null,
                statusCode = HttpStatusCode.InternalServerError.value
            )
        }
    }

    override suspend fun deleteNote(noteId: String?): ApiResponse<Note?> {
        return try {

            if (noteId.isNullOrBlank()){
                return  ApiResponse(
                    message = listOf("Note id not null or blank"),
                    succeeded = false,
                    totalItems = 0,
                    data = null,
                    statusCode = HttpStatusCode.NotFound.value
                )

            }

            val response = noteRepo.deleteNote(noteId)
            if (response != null){
                ApiResponse(
                    message = listOf("Note delete successfully"),
                    succeeded = true,
                    totalItems = 1,
                    type = "Note",
                    data = response,
                    statusCode = HttpStatusCode.OK.value
                )
            }
            else{
                ApiResponse(
                    message = listOf("Failed to get note"),
                    succeeded = false,
                    totalItems = 1,
                    data = null,
                    statusCode = HttpStatusCode.BadRequest.value
                )
            }
        }
        catch (e : Exception){
            ApiResponse(
                message = listOf("Failed to delete note"),
                succeeded = false,
                totalItems = 0,
                data = null,
                statusCode = HttpStatusCode.InternalServerError.value
            )
        }
    }

}