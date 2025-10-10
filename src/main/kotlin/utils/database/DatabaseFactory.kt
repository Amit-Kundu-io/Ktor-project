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
        if (initialized) return
        initialized = true

        val env = System.getenv("APP_ENV") ?: "local"
        val dotenv = if (env == "local") io.github.cdimascio.dotenv.dotenv() else null

        fun getEnv(key: String, default: String? = null): String =
            System.getenv(key)
                ?: dotenv?.get(key)
                ?: default
                ?: throw IllegalStateException("Missing environment variable: $key")

        val config = HikariConfig().apply {
            jdbcUrl = getEnv("DB_URL")
            driverClassName = getEnv("DB_DRIVER", "org.postgresql.Driver")
            username = getEnv("DB_USER")
            password = getEnv("DB_PASSWORD")
            maximumPoolSize = 20
            minimumIdle = 5
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            idleTimeout = 600_000
            maxLifetime = 1_800_000
            initializationFailTimeout = 1  // Fail fast if DB is unreachable
            validate()
        }

        val dataSource = HikariDataSource(config)
        Database.connect(dataSource)

        // Warm-up connection pool
        transaction {
            exec("SELECT 1;")
        }

            transaction {
                SchemaUtils.createMissingTablesAndColumns(UserTable, NoteTable)
            }


        println("✅ Database initialized once.")
    }
}
