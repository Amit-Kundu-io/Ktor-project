package com.a.features.auth.routes

import com.a.NotesApp.features.auth.models.RegisterRequest
import com.a.NotesApp.features.auth.service.AuthService
import com.a.features.auth.data.models.LoginRequest
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

            post("/login") {
                val request = call.receive<LoginRequest>()
                val response = authService.loginUser(request)
                call.respond(status = HttpStatusCode.fromValue(response.statusCode), message = response)
            }
        }
    }
}

