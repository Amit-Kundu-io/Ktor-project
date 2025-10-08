package com.a.features.auth.routes

import com.a.NotesApp.features.auth.models.RegisterRequest
import com.a.NotesApp.features.auth.service.AuthService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.respond
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.authRouts() {

        val authService: AuthService by inject()

    routing {
        route("api/users") {
            post("/register") {
                val request = call.receive<RegisterRequest>()
                val response = authService.createUser(request)
                call.respond(status = HttpStatusCode.fromValue(response.statusCode), message = response)
            }
        }
    }
}

