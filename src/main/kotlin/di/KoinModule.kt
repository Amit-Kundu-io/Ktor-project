package com.a.di


import com.a.note_app.features.auth.authDi.authModule
import com.a.note_app.features.notes.noteDi.noteModule
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.fileProperties
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()
        modules(authModule, noteModule)
    }
}
