package com.dms.driver.di

import com.dms.driver.repositories.KmongoDriverRepository
import com.dms.driver.services.DriverService
import com.dms.driver.repositories.DriverRepository
import com.mongodb.ConnectionString
import org.koin.dsl.module
import org.litote.kmongo.reactivestreams.KMongo
import org.litote.kmongo.coroutine.coroutine

val driverModule = module {

    single {
        val uri = System.getenv("MONGO_URI") ?: "mongodb://mongo:27017"
        KMongo.createClient(ConnectionString(uri)).coroutine
    }

    single {
        val dbName = System.getenv("MONGO_DB") ?: "delivery"
        get<org.litote.kmongo.coroutine.CoroutineClient>().getDatabase(dbName)
    }

    single<DriverRepository> { KmongoDriverRepository(get()) }
    single { DriverService(get()) }
}