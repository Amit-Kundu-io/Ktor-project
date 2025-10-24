package com.a

import io.ktor.server.application.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml")
    }

    routing {
        get("/") {

            call.respondText("Hello World")

        }

    }

}
