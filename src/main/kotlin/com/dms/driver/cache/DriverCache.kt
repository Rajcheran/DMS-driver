package com.dms.driver.cache

interface DriverCache {

    suspend fun setStatus(driverId: String, status: String)

    suspend fun getStatus(driverId: String): String?

    suspend fun incrementAssignmentCount(driverId: String)

    suspend fun getAssignmentCount(driverId: String): Int
}
