package com.a.utils.helper

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlin.coroutines.CoroutineContext

//suspend fun <T : Any> dbQuery(block: suspend () -> T?): T? =
//    newSuspendedTransaction(Dispatchers.IO) { block() }

suspend fun <T> dbQuery(block: Transaction.() -> T): T =
    newSuspendedTransaction(Dispatchers.IO) { block() }

