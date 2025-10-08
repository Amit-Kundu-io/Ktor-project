package com.a.utils.helper

import java.util.*
import kotlin.random.Random

fun userIdGenerate(): String {
    // random 24-character hex string
    val bytes = ByteArray(12)
    Random.nextBytes(bytes)
    return bytes.joinToString("") { "%02x".format(it) }
}
