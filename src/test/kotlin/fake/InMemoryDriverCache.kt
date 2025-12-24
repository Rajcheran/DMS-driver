package com.dms.fake

import com.dms.driver.domain.DriverStatus
import com.dms.driver.cache.DriverCache

class InMemoryDriverCache : DriverCache {

    private val status = mutableMapOf<String, DriverStatus>()
    private val counts = mutableMapOf<String, Int>()

    override suspend fun setStatus(driverId: String, status: DriverStatus) {
        this.status[driverId] = status
    }

    override suspend fun getStatus(driverId: String): DriverStatus? {
        return status[driverId]
    }

    override suspend fun incrementAssignmentCount(driverId: String) {
        counts[driverId] = (counts[driverId] ?: 0) + 1
    }

    override suspend fun getAssignmentCount(driverId: String): Int {
        return counts[driverId] ?: 0
    }

    override suspend fun clear(driverId: String) {
        status.remove(driverId)
        counts.remove(driverId)
    }
}