package com.dms.driver

import com.dms.driver.di.appModules
import org.koin.ktor.plugin.Koin
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    install(Koin) { modules(appModules) }
    configureSerialization()
    configureHTTP()
    configureRouting()

}
