package com.a.utils.helper


import io.ktor.server.plugins.statuspages.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*


fun Application.configureStatusPages() {
    install(StatusPages) {
        status(HttpStatusCode.NotFound) { call, status ->
            call.respond(
                HttpStatusCode.NotFound,
                ApiResponse(
                    message = listOf("Endpoint not found"),
                    succeeded = false,
                    totalItems = 0,
                    type = "Error",
                    data = null,
                    statusCode = 404
                )
            )
        }

        exception<Throwable> { call, cause ->
            call.respond(
                HttpStatusCode.InternalServerError,
                ApiResponse(
                    message = listOf("Internal server error: ${cause.localizedMessage}"),
                    succeeded = false,
                    totalItems = 0,
                    type = "Error",
                    data = null,
                    statusCode = 500
                )
            )
        }
    }
}
