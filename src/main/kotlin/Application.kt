package com.a

import com.a.di.configureKoin
import com.a.features.auth.routes.authRouts
import com.a.plugins.serializationPlugin
import com.a.utils.database.initDatabase
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    initDatabase()
    configureKoin()
    configureRouting()
    serializationPlugin()
    authRouts()

}
