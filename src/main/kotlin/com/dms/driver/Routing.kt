package com.dms.driver

import com.dms.driver.routes.driverRoutes
import com.dms.driver.services.DriverService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

fun Application.configureRouting() {
    val driverService = get<DriverService>()
    routing {
        get("/health") {
            call.respondText("Hello World!")
        }
        driverRoutes(driverService)
    }
}
