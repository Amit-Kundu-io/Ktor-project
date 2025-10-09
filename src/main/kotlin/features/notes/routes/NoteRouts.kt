package com.a.features.notes.routes

import com.a.NotesApp.features.auth.models.RegisterRequest
import com.a.NotesApp.features.auth.service.AuthService
import com.a.features.auth.data.models.LoginRequest
import com.a.features.notes.data.models.NoteRequest
import com.a.features.notes.domain.service.NoteServices
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject
import kotlin.getValue

fun Application.noteRouts() {

    val services: NoteServices by inject()

    routing {
        route("api/notes") {
            post("/createandupdate") {
                val request = call.receive<NoteRequest>()
                val response = services.createAndUpdateNote(request)
                call.respond(status = HttpStatusCode.fromValue(response.statusCode), message = response)
            }

            get("/notes"){
                val userId = call.request.queryParameters["userId"]
                val response = services.getAllNotes(userId)
                call.respond(status = HttpStatusCode.fromValue(response.statusCode), message = response)
            }

            delete("/delete") {
                val noteId = call.request.queryParameters["noteId"] ?: throw BadRequestException("Missing note ID")
                val response = services.deleteNote(noteId)
                call.respond(status = HttpStatusCode.fromValue(response.statusCode), message = response)
            }
        }
    }
}