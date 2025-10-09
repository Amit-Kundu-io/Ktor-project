package com.a.utils

import io.ktor.server.application.Application
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import javax.swing.text.DefaultStyledDocument.ElementSpec.ContentType
import io.ktor.http.*

fun Application.docsRoutes() {
    routing {
        get("/docs") {
            val mdText = java.io.File("docs/noteApi.md").readText()
            call.respondText(mdText)  // Plain text
        }
    }
}


