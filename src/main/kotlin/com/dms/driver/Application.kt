package com.dms.driver

import com.dms.driver.di.appModules
import com.dms.driver.infrastructure.kafka.installKafkaConsumers
import org.koin.ktor.plugin.Koin
import io.ktor.server.application.*
import org.koin.core.module.Module

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module(
    enableKafka: Boolean = true,
    extraModules: List<Module> = emptyList()
) {
    install(Koin) {
        modules(appModules + extraModules)
    }

    if (enableKafka) {
        installKafkaConsumers()
    }

    configureSerialization()
    configureHTTP()
    configureRouting()
}

