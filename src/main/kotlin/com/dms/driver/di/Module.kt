package com.dms.driver.di

import com.dms.driver.repositories.KafkaEventPublisher
import com.dms.driver.repositories.KmongoAssignmentRepository
import com.dms.driver.repositories.KmongoDriverRepository
import com.dms.driver.services.AssignmentService
import com.dms.driver.services.DriverService
import com.dms.driver.repositories.AssignmentRepository
import com.dms.driver.repositories.DriverRepository
import com.dms.driver.repositories.EventPublisher
import org.koin.dsl.module
import org.litote.kmongo.reactivestreams.KMongo
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.CoroutineClient
import com.mongodb.ConnectionString
import org.litote.kmongo.coroutine.coroutine

val driverKoinModule = module {
    single<CoroutineClient> {
        val uri = System.getenv("MONGO_URI") ?: "mongodb://mongo:27017"
        KMongo.createClient(ConnectionString(uri)).coroutine
    }

    single<CoroutineDatabase> {
        val dbName = System.getenv("MONGO_DB") ?: "delivery"
        get<CoroutineClient>().getDatabase(dbName)
    }

    single<DriverRepository> { KmongoDriverRepository(get()) }
    single<AssignmentRepository> { KmongoAssignmentRepository(get()) }

    single<EventPublisher> {
        val bs = System.getenv("KAFKA_BOOTSTRAP") ?: "kafka:9092"
        KafkaEventPublisher(bs)
    }

    single { DriverService(get()) }
    single { AssignmentService(get(), get()) }
}