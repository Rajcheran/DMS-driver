package com.dms.driver.cache

import com.dms.driver.domain.DriverStatus

interface DriverCache {

    suspend fun setStatus(driverId: String, status: DriverStatus)

    suspend fun getStatus(driverId: String): DriverStatus?

    suspend fun incrementAssignmentCount(driverId: String)

    suspend fun getAssignmentCount(driverId: String): Int

    suspend fun clear(driverId: String)
}
