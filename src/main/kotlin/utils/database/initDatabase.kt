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


    val env = System.getenv("APP_ENV") ?: "local"
    val dotenv = if (env == "local") io.github.cdimascio.dotenv.dotenv() else null

    fun getEnv(key: String, default: String? = null): String =
        System.getenv(key)
            ?: dotenv?.get(key)
            ?: default
            ?: throw IllegalStateException("Missing environment variable: $key")

    val port = getEnv("PORT", "8080").toInt()
    val url = getEnv("DB_URL")
    val driver = getEnv("DB_DRIVER", "org.postgresql.Driver")
    val user = getEnv("DB_USER")
    val password = getEnv("DB_PASSWORD")


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