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
import kotlin.system.measureTimeMillis

fun Application.noteRouts(services: NoteServices) {
    routing {
        route("/api/notes") {

            post("/createandupdate") {
                val request = try {
                    call.receive<NoteRequest>()
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid note data")
                    return@post
                }

                val response = services.createAndUpdateNote(request)
                call.respond(HttpStatusCode.fromValue(response.statusCode), response)
            }
            get("/getnotes") {
                val totalTime = measureTimeMillis {
                    val userId = call.request.queryParameters["userId"]
                    if (userId.isNullOrBlank()) {
                        call.respond(HttpStatusCode.BadRequest, "Missing or invalid userId")
                        return@get
                    }

                    val responseTime = measureTimeMillis {
                        val response = services.getAllNotes(userId)
                        call.respond(HttpStatusCode.fromValue(response.statusCode), response)
                    }

                    println("⏱ Response construction took $responseTime ms")
                }

                println("⏱ Total /getnotes API time: $totalTime ms")
            }


            /*
            get("/getnotes") {
                val userId = call.request.queryParameters["userId"]
                if (userId.isNullOrBlank()) {
                    call.respond(HttpStatusCode.BadRequest, "Missing or invalid userId")
                    return@get
                }

                    val response = services.getAllNotes(userId)
                    call.respond(HttpStatusCode.fromValue(response.statusCode), response)

            }

             */

            delete("/delete") {
                val noteId = call.request.queryParameters["noteId"]
                if (noteId.isNullOrBlank()) {
                    call.respond(HttpStatusCode.BadRequest, "Missing or invalid noteId")
                    return@delete
                }

                val response = services.deleteNote(noteId)
                call.respond(HttpStatusCode.fromValue(response.statusCode), response)
            }
        }
    }
}
