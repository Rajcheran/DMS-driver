package com.dms.driver.services

import com.dms.driver.domain.Driver
import com.dms.driver.repositories.DriverRepository

class DriverService(
    private val repo: DriverRepository
) {
    suspend fun register(driver: Driver): Driver {
        println("Registering driver: $driver")
        require(driver.name.isNotBlank()) { "Driver name required" }
        require(driver.phone.isNotBlank()) { "Phone required" }
        return repo.save(driver)
    }

    suspend fun get(id: String): Driver? =
        repo.findById(id)

    suspend fun listByAgency(agencyId: String): List<Driver> =
        repo.findByAgency(agencyId)
}