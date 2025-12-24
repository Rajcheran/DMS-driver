package com.dms.driver.test.fakes

import com.dms.driver.domain.Driver
import com.dms.driver.domain.DriverStatus
import com.dms.driver.repositories.DriverRepository

class InMemoryDriverRepository : DriverRepository {

    private val store = mutableMapOf<String, Driver>()

    override suspend fun save(driver: Driver): Driver {
        store[driver.id] = driver
        return driver
    }

    override suspend fun findById(id: String): Driver? =
        store[id]

    override suspend fun findByAgency(agencyId: String): List<Driver> =
        store.values.filter { it.agencyId == agencyId }

    override suspend fun updateStatus(driverId: String, status: DriverStatus) {
        store[driverId]?.let {
            store[driverId] = it.copy(status = status)
        }
    }
}
