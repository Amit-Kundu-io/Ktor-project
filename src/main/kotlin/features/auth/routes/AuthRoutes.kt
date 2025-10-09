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
fun Application.authRouts(authService: AuthService) {
    routing {
        route("/api/users") {
            post("/register") {
                val request = try {
                    call.receive<RegisterRequest>()
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid registration data")
                    return@post
                }

                val response = authService.createUser(request)
                call.respond(HttpStatusCode.fromValue(response.statusCode), response)
            }

            post("/login") {
                val request = try {
                    call.receive<LoginRequest>()
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid login data")
                    return@post
                }

                val response = authService.loginUser(request)
                call.respond(HttpStatusCode.fromValue(response.statusCode), response)
            }
        }
    }
}


