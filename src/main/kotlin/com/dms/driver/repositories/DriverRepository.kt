package com.dms.driver.repositories

import com.dms.driver.domain.Driver
import com.dms.driver.domain.DriverStatus

interface DriverRepository {

    suspend fun save(driver: Driver): Driver

    suspend fun findById(id: String): Driver?

    suspend fun findByAgency(agencyId: String): List<Driver>

    suspend fun updateStatus(driverId: String, status: DriverStatus)
}
