package com.a.utils.helper

import kotlin.random.Random


fun idGenerate(): String {
    val bytes = ByteArray(12)
    Random.nextBytes(bytes)
    return bytes.joinToString("") { "%02x".format(it) }
}

