package com.dms.driver.routes

import com.dms.driver.services.AssignmentService
import com.dms.driver.services.DriverService
import com.dms.driver.domain.CreateDriverRequest
import com.dms.driver.domain.AssignmentRequest
import com.dms.driver.domain.Driver
import com.dms.driver.domain.Assignment
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.http.*
import org.koin.ktor.ext.inject

fun Application.driverApiRoutes(driverService:DriverService,assignmentService: AssignmentService) {

    routing {
//        get("/health") {
//            call.respond(mapOf("status" to "ok", "service" to "driver-service"))
//        }

        route("/drivers") {

            post {
                val driver = call.receive<Driver>()
                val saved = driverService.register(driver)
                call.respond(HttpStatusCode.Created, saved)
            }

            get("/{id}") {
                val id = call.parameters["id"]!!
                val driver = driverService.get(id)
                if (driver == null)
                    call.respond(HttpStatusCode.NotFound)
                else
                    call.respond(driver)
            }

            get("/agency/{agencyId}") {
                val agencyId = call.parameters["agencyId"]!!
                call.respond(driverService.listByAgency(agencyId))
            }
        }
    }
}