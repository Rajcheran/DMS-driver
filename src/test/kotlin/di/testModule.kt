package com.dms.di

import com.dms.driver.cache.DriverCache
import com.dms.driver.messaging.domain.AssignmentEventConsumer
import com.dms.driver.repositories.DriverRepository
import com.dms.fake.InMemoryDriverCache
import com.dms.driver.test.fakes.*
import org.koin.dsl.module

val testModule = module(override = true) {

    single<DriverRepository> { InMemoryDriverRepository() }

    single<DriverCache> { InMemoryDriverCache() }

    single<AssignmentEventConsumer> { FakeAssignmentConsumer() }
}
