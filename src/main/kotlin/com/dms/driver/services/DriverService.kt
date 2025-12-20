package com.dms.driver.services

import com.dms.driver.cache.DriverCache
import com.dms.driver.domain.Driver
import com.dms.driver.repositories.DriverRepository

class DriverService(
    private val repo: DriverRepository, private val cache: DriverCache
) {
    suspend fun register(driver: Driver): Driver {
        println("Registering driver: $driver")
        require(driver.name.isNotBlank()) { "Driver name required" }
        require(driver.phone.isNotBlank()) { "Phone required" }
        val saved = repo.save(driver)
        cache.setStatus(saved.id, saved.status.name)
        return saved
    }

    suspend fun get(id: String): Driver? =
        repo.findById(id)

    suspend fun listByAgency(agencyId: String): List<Driver> =
        repo.findByAgency(agencyId)
    suspend fun handleAssignment(
        driverId: String,
        assignmentId: String
    ) {
        val driver = repo.findById(driverId) ?: return

        // For now: just log / ensure driver exists
        cache.incrementAssignmentCount(driverId)

        println("Driver ${driver.id} assigned assignment $assignmentId")
    }
}