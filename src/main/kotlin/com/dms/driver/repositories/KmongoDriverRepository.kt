package com.dms.driver.repositories

import com.dms.driver.domain.Driver
import com.dms.driver.domain.DriverDocument
import com.dms.driver.repositories.DriverRepository
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.getCollection

class KmongoDriverRepository(
    db: CoroutineDatabase
) : DriverRepository {

    private val collection = db.getCollection<DriverDocument>("drivers")

    override suspend fun save(driver: Driver): Driver {
        val doc = DriverDocument.fromDomain(driver)
        println(doc)
        val res = collection.insertOne(doc)
        println(res)
        return driver
    }

    override suspend fun findById(id: String): Driver? =
        collection.findOne(DriverDocument::_id eq id)?.toDomain()

    override suspend fun findByAgency(agencyId: String): List<Driver> =
        collection.find(DriverDocument::agencyId eq agencyId)
            .toList()
            .map { it.toDomain() }
}