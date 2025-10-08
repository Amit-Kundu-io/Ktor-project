package com.a

import com.a.di.configureKoin
import com.a.features.auth.routes.authRouts
import com.a.features.notes.routes.noteRouts
import com.a.plugins.serializationPlugin
import com.a.utils.database.initDatabase
import io.github.cdimascio.dotenv.dotenv
import io.ktor.server.application.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

//fun main(args: Array<String>) {
//    io.ktor.server.netty.EngineMain.main(args)
//}

fun main() {
    val dotenv = dotenv()
    val env = System.getenv("APP_ENV") ?: "local"
    val port = System.getenv("PORT") ?: when (env) {
        "local" -> dotenv["PORT"]
        else -> "8080"
    }

    embeddedServer(Netty, port = port.toInt() , module = Application::module)
        .start(wait = true)
}


fun Application.module() {
    initDatabase()
    configureKoin()
    configureRouting()
    serializationPlugin()
    authRouts()
    noteRouts()

}
