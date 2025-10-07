package com.a.db

import com.a.db.table.UserTable
import io.github.cdimascio.dotenv.dotenv
import io.ktor.http.ContentType
import io.ktor.server.application.Application
import io.ktor.server.util.url
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.initDB() {

    val dotenv = dotenv()

    val url = dotenv["DB_URL"]
    val driver = dotenv["DB_DRIVER"] ?: "org.postgresql.Driver"
    val user = dotenv["DB_USER"]
    val password = dotenv["DB_PASSWORD"]
    /*
    val url = System.getenv("DB_URL")
    val driver = System.getenv("DB_DRIVER")
    val user = System.getenv("DB_USER")
    val password = System.getenv("DB_PASSWORD")

     */

    val db = Database.connect(
        url = url,
        driver = driver,
        user = user,
        password = password
    )
    transaction (db){
        SchemaUtils.create(UserTable)
    }
}