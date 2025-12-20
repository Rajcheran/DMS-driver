package com.dms.driver.di


import com.dms.driver.messaging.consumer.DriverAssignmentConsumer
import org.koin.dsl.module


val kafkaModule = module {
    single {
        DriverAssignmentConsumer(get())
    }

}