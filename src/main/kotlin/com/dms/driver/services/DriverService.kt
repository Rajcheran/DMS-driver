package com.dms.driver.services

import com.dms.driver.cache.DriverCache
import com.dms.driver.domain.Driver
import com.dms.driver.domain.DriverStatus
import com.dms.driver.domain.DriverOperationalState
import com.dms.driver.repositories.DriverRepository

class DriverService(
    private val repository: DriverRepository,
    private val cache: DriverCache
) {

    suspend fun register(driver: Driver) {
        // 1️⃣ Persist canonical data
        repository.save(driver)

        // 2️⃣ Initialize operational state
        cache.setStatus(driver.id, DriverStatus.ACTIVE)
    }
    suspend fun handleAssignment(
        driverId: String,
        assignmentId: String
    ) {
        // 1️⃣ Mark driver busy in Redis (fast)
        cache.setStatus(driverId, DriverStatus.BUSY)

        // 2️⃣ Persist status in Mongo (source of truth)
        repository.updateStatus(driverId, DriverStatus.BUSY)

        // 3️⃣ Track assignment count
        cache.incrementAssignmentCount(driverId)

        println("Driver $driverId assigned assignment $assignmentId")
    }
    suspend fun get(id: String): Driver? =
        repository.findById(id)
    suspend fun listByAgency(agencyId: String): List<Driver> =
        repository.findByAgency(agencyId)


    suspend fun markActive(driverId: String) {
        repository.updateStatus(driverId, DriverStatus.ACTIVE)
        cache.setStatus(driverId, DriverStatus.ACTIVE)
    }

    suspend fun markInactive(driverId: String) {
        repository.updateStatus(driverId, DriverStatus.INACTIVE)
        cache.setStatus(driverId, DriverStatus.INACTIVE)
    }

    suspend fun incrementLoad(driverId: String) {
        cache.incrementAssignmentCount(driverId)
    }

    suspend fun getOperationalState(driverId: String): DriverOperationalState {
        return DriverOperationalState(
            status = cache.getStatus(driverId) ?: DriverStatus.INACTIVE,
            assignmentCount = cache.getAssignmentCount(driverId)
        )
    }
}
