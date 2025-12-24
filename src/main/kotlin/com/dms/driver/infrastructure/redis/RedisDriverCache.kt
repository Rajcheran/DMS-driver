package com.dms.driver.infrastructure.redis

import com.dms.driver.cache.DriverCache
import com.dms.driver.domain.DriverStatus
import io.lettuce.core.RedisClient
import io.lettuce.core.api.coroutines.RedisCoroutinesCommands
import io.lettuce.core.api.coroutines


class RedisDriverCache(
    private val redisClient: RedisClient
) : DriverCache {

    private val redis: RedisCoroutinesCommands<String, String> by lazy {
        redisClient.connect().coroutines()
    }

    override suspend fun setStatus(driverId: String, status: DriverStatus) {
        redis.set("driver:$driverId:status", status.name)
    }

    override suspend fun getStatus(driverId: String): DriverStatus? {
        return redis.get("driver:$driverId:status")
            ?.let { DriverStatus.valueOf(it) }
    }

    override suspend fun incrementAssignmentCount(driverId: String) {
        redis.incr("driver:$driverId:assignment_count")
    }

    override suspend fun getAssignmentCount(driverId: String): Int {
        return redis.get("driver:$driverId:assignment_count")?.toInt() ?: 0
    }

    override suspend fun clear(driverId: String) {
        redis.del(
            "driver:$driverId:status",
            "driver:$driverId:assignment_count"
        )
    }
}
