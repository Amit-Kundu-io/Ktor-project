package com.a.utils.database

import com.a.NotesApp.features.auth.tables.UserTable
import com.a.features.notes.data.table.NoteTable
import io.github.cdimascio.dotenv.dotenv
import io.ktor.server.application.Application
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction


fun Application.initDatabase() {

/*

    val url = dotenv["DB_URL"]
    val driver = dotenv["DB_DRIVER"] ?: "org.postgresql.Driver"
    val user = dotenv["DB_USER"]
    val password = dotenv["DB_PASSWORD"]

 */

    /*

    val url = System.getenv("DB_URL") ?: throw IllegalStateException("Missing DB_URL")
    val driver = System.getenv("DB_DRIVER") ?: "org.postgresql.Driver"
    val user = System.getenv("DB_USER") ?: throw IllegalStateException("Missing DB_USER")
    val password = System.getenv("DB_PASSWORD") ?: throw IllegalStateException("Missing DB_PASSWORD")

     */

    val dotenv = dotenv()
    val env = System.getenv("APP_ENV") ?: "local"

    val url = System.getenv("DB_URL") ?: when (env) {
        "local" -> dotenv["DB_URL"]
        else -> throw IllegalStateException("Missing DB_URL")
    }

    val driver = System.getenv("DB_DRIVER") ?: "org.postgresql.Driver"
    val user = System.getenv("DB_USER") ?: when (env) {
        "local" -> dotenv["DB_USER"]
        else -> throw IllegalStateException("Missing DB_USER")
    }

    val password = System.getenv("DB_PASSWORD") ?: when (env) {
        "local" -> dotenv["DB_PASSWORD"]
        else -> throw IllegalStateException("Missing DB_PASSWORD")
    }




    val db = Database.connect(
        url = url,
        driver = driver,
        user = user,
        password = password
    )
    transaction (db){
        SchemaUtils.create(UserTable)
        SchemaUtils.create(NoteTable)
    }
}