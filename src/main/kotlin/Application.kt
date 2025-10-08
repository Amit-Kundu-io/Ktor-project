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
    // Detect environment (default: local)
    val env = System.getenv("APP_ENV") ?: "local"

    // Use Render's dynamic port if available
    val port = System.getenv("PORT")?.toIntOrNull() ?: 8080

    // Bind host based on environment
    val host = if (env == "local") "127.0.0.1" else "0.0.0.0"

    embeddedServer(Netty, host = host, port = port, module = Application::module)
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
