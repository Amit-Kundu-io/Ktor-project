package com.a.routes

import com.a.domain.models.RegisterRequest
import com.a.domain.services.UserServices
import io.ktor.client.statement.HttpStatement
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing

fun Application.userRouts(userServices: UserServices) {
    routing {
        route("api/users") {
            post("/register") {
                val userName = call.request.queryParameters["userName"]
                val password = call.request.queryParameters["password"]

                if (userName.isNullOrBlank() || password.isNullOrBlank()) {
                    call.respond(HttpStatusCode.BadRequest, "Missing username or password")
                    return@post
                }

                val request = RegisterRequest(userName, password)

                val user = userServices.registerUser(request)
                if (user != null) {
                    call.respond(HttpStatusCode.Created, user)
                } else {
                    call.respond(HttpStatusCode.Conflict, "User already exists")
                }

                /*
                val request = call.receive<RegisterRequest>()
                val user = userServices.registerUser(request)
                if (user!= null){
                    call.respond(HttpStatusCode.Created,user)
                }
                else{
                    call.respond(HttpStatusCode.Conflict,"User already exists")
                }

                 */
            }
        }
    }
}
