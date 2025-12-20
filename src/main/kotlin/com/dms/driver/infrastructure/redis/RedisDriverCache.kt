package com.dms.driver.infrastructure.redis

import com.dms.driver.cache.DriverCache
import io.lettuce.core.api.coroutines.RedisCoroutinesCommands

class RedisDriverCache(
    private val redis: RedisCoroutinesCommands<String, String>
) : DriverCache {

    override suspend fun setStatus(driverId: String, status: String) {
        redis.set("driver:$driverId:status", status)
    }

    override suspend fun getStatus(driverId: String): String? {
        return redis.get("driver:$driverId:status")
    }

    override suspend fun incrementAssignmentCount(driverId: String) {
        redis.incr("driver:$driverId:assignment_count")
    }

    override suspend fun getAssignmentCount(driverId: String): Int {
        return redis.get("driver:$driverId:assignment_count")?.toInt() ?: 0
    }
}
