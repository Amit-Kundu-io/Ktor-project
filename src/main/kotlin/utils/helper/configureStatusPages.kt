package com.a.utils.helper


import io.ktor.server.plugins.statuspages.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.plugins.NotFoundException
import io.ktor.server.response.*


fun Application.configureStatusPages() {
    install(StatusPages) {

        // 404 Not Found (wrong endpoint)
        status(HttpStatusCode.NotFound) { call, status ->
            call.respond(
                HttpStatusCode.NotFound,
                ApiResponse(
                    message = listOf(status.description),
                    succeeded = false,
                    totalItems = 0,
                    type = "Error",
                    data = null,
                    statusCode = 404
                )
            )
        }

        // 405 Method Not Allowed (wrong HTTP method)
        status(HttpStatusCode.MethodNotAllowed) { call, status ->
            call.respond(
                HttpStatusCode.MethodNotAllowed,
                ApiResponse(
                    message = listOf(status.description),
                    succeeded = false,
                    totalItems = 0,
                    type = "Error",
                    data = null,
                    statusCode = status.value
                )
            )
        }

        // 400 Bad Request (invalid or missing parameters)
        exception<BadRequestException> { call, cause ->
            call.respond(
                HttpStatusCode.BadRequest,
                ApiResponse(
                    message = listOf(cause.localizedMessage),
                    succeeded = false,
                    totalItems = 0,
                    type = "Error",
                    data = null,
                    statusCode = 400
                )
            )
        }

        // 404 Resource Not Found (e.g., note not found)
        exception<NotFoundException> { call, cause ->
            call.respond(
                HttpStatusCode.NotFound,
                ApiResponse(
                    message = listOf(cause.localizedMessage),
                    succeeded = false,
                    totalItems = 0,
                    type = "Error",
                    data = null,
                    statusCode = 404
                )
            )
        }

        // 500 Internal Server Error (unexpected exceptions)
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
