package com.dms

import com.dms.driver.module
import com.dms.di.testModule
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {

    @Test
    fun health_check_works() = testApplication {
        application {
            module(
                enableKafka = false,
                extraModules = listOf(testModule)
            )
        }

        val response = client.get("/health")
        assertEquals(HttpStatusCode.OK, response.status)
    }

}
