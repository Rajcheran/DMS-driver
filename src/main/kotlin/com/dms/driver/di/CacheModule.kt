package com.dms.driver.di

import com.dms.driver.cache.DriverCache
import com.dms.driver.infrastructure.redis.RedisDriverCache
import io.lettuce.core.api.coroutines

import io.lettuce.core.RedisClient
import org.koin.dsl.module


val cacheModule = module {

    single {
        RedisClient.create(
            System.getenv("REDIS_URI") ?: "redis://redis:6379"
        )
    }

    single {
        get<RedisClient>().connect().coroutines()
    }

    single<DriverCache> {
        RedisDriverCache(get())
    }

}