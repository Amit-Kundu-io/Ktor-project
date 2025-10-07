package com.a

import com.a.db.initDB
import com.a.di.configureKoin
import com.a.domain.services.UserServices
import com.a.routes.userRouts
import io.ktor.server.application.*
import org.koin.ktor.ext.get

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
   //    configureRouting()
    configureKoin()
    initDB()
    configureContentNegotiation()

    val userServices = get<UserServices>()
    userRouts(userServices)
    configureRouting()
}
