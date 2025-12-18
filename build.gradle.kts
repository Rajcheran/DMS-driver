plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlin.plugin.serialization)
}

group = "com.dms"
version = "0.0.1"

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.cors)
    implementation(libs.ktor.server.netty)
    implementation(libs.logback.classic)
    implementation(libs.ktor.server.config.yaml)
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)

    implementation(libs.koin.core)
    implementation(libs.koin.ktor)
    implementation(libs.koin.logger.slf4j)

    implementation(libs.kmongo.core)
    implementation(libs.kmongo.coroutine)

    implementation(libs.kafka.clients)
    implementation(libs.lettuce.core)
}

jib {
    from {
        image = "eclipse-temurin:17-jre"
    }
    to {
        image = providers.environmentVariable("JIB_TO_IMAGE").orElse("localhost/driver-service:latest").get()
    }
    container {
        mainClass = "com.dms.driver.ApplicationKt"
        args = listOf()
        jvmFlags = listOf("-Xms256m", "-Xmx512m")
        ports = listOf("8084")
        // <- CORRECT VALUE:
        creationTime = "USE_CURRENT_TIMESTAMP"
    }
}
