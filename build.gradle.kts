plugins {
    kotlin("jvm") version "1.9.0"
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlin.plugin.serialization)
    application
}

group = "com.a"
version = "0.0.1"

application {
    //mainClass.set("io.ktor.server.netty.EngineMain")
    mainClass.set("com.a.ApplicationKt") 

}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.exposed.core)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.dao)
    implementation(libs.h2)
    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.server.auth.jwt)
    implementation(libs.ktor.server.netty)
    implementation(libs.logback.classic)
    implementation(libs.ktor.server.config.yaml)

    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)

    // Koin
    implementation("io.insert-koin:koin-ktor:3.5.6")
    implementation("io.insert-koin:koin-logger-slf4j:3.5.6")
    implementation("io.github.cdimascio:dotenv-kotlin:6.4.1")
    implementation("org.postgresql:postgresql:42.7.3")
    implementation("org.mindrot:jbcrypt:0.4")


    implementation("io.ktor:ktor-server-status-pages-jvm")
    implementation("com.zaxxer:HikariCP:5.0.1")
}
