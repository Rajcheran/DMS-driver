package com.dms.driver.routes

import com.dms.driver.services.DriverService
import com.dms.driver.domain.Driver
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.http.*
import org.koin.java.KoinJavaComponent.get

fun Route.driverRoutes(service: DriverService) {

    route("/drivers") {

        post {
            println("inside post method 1")

            val driver = call.receive<Driver>()
            println("inside post method")
            val saved = service.register(driver)

            call.respond(HttpStatusCode.Created, saved)
        }

        get("/{id}") {
//            val service: DriverService = get()
            val id = call.parameters["id"]!!
            val driver = service.get(id)
            if (driver == null)
                call.respond(HttpStatusCode.NotFound)
            else
                call.respond(driver)
        }

        get("/agency/{agencyId}") {
//            val service: DriverService = get()
            val agencyId = call.parameters["agencyId"]!!
            call.respond(service.listByAgency(agencyId))
        }
        get("/driver"){
            call.respondText("Hello in driver in driver!")
        }
    }

    }