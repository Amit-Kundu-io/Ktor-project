package com.a

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.*
import kotlin.system.measureTimeMillis

fun Application.configureRouting() {

        routing {

            get("/") {
                val time = measureTimeMillis {
                call.respondText("Hello World!")
            }
                print("time checking $time ms")
        }

    }

}
