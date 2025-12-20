// test/fakes/FakeDriverCache.kt
package com.dms.driver.test.fakes

import com.dms.driver.cache.DriverCache
import java.util.concurrent.ConcurrentHashMap

class FakeDriverCache : DriverCache {

    private val status = ConcurrentHashMap<String, String>()
    private val counts = ConcurrentHashMap<String, Int>()

    override suspend fun setStatus(driverId: String, status: String) {
        this.status[driverId] = status
    }

    override suspend fun getStatus(driverId: String): String? {
        TODO("Not yet implemented")
    }

    override suspend fun incrementAssignmentCount(driverId: String) {
        counts[driverId] = (counts[driverId] ?: 0) + 1
    }

    override suspend fun getAssignmentCount(driverId: String): Int {
        return counts[driverId] ?: 0
    }
}
