package com.a.utils.database

import com.a.NotesApp.features.auth.tables.UserTable
import com.a.features.notes.data.table.NoteTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    private var initialized = false

    fun init() {
        if (initialized) return // prevent multiple init
        initialized = true

        val env = System.getenv("APP_ENV") ?: "local"
        val dotenv = if (env == "local") io.github.cdimascio.dotenv.dotenv() else null

        fun getEnv(key: String, default: String? = null): String =
            System.getenv(key)
                ?: dotenv?.get(key)
                ?: default
                ?: throw IllegalStateException("Missing environment variable: $key")

        val url = getEnv("DB_URL")
        val driver = getEnv("DB_DRIVER", "org.postgresql.Driver")
        val user = getEnv("DB_USER")
        val dbPassword = getEnv("DB_PASSWORD")

        // HikariCP configuration for connection pooling
        val config = HikariConfig().apply {
            jdbcUrl = url
            driverClassName = driver
            username = user
            password = dbPassword
            maximumPoolSize = 10   // maximum connections
            minimumIdle = 5        // minimum idle connections
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        }

        val dataSource = HikariDataSource(config)

        // Connect Exposed to HikariCP
        Database.connect(dataSource)

        transaction {
            SchemaUtils.createMissingTablesAndColumns(UserTable, NoteTable)
        }

        println("✅ Database initialized once.")
    }
}
