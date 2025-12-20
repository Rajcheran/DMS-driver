package com.dms

import com.dms.driver.cache.DriverCache
import com.dms.driver.module
import com.dms.driver.test.fakes.FakeDriverCache
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.koin.dsl.module
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {

    @Test
    fun testRoot() = testApplication {
        application {
            val testCacheModule = module {
                single<DriverCache> { FakeDriverCache() }
            }
            module(
                enableKafka = false,
                extraModules = listOf(testCacheModule)
            )
        }

        client.get("/health").apply {
            assertEquals(HttpStatusCode.OK, status)
        }
    }

}
