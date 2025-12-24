package com.dms.driver.repositories

import com.dms.driver.domain.Driver
import com.dms.driver.domain.DriverStatus
import com.dms.driver.domain.DriverDocument
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.setValue

class KmongoDriverRepository(
    db: CoroutineDatabase
) : DriverRepository {

    private val collection = db.getCollection<DriverDocument>("drivers")

    override suspend fun save(driver: Driver): Driver {
        collection.insertOne(DriverDocument.fromDomain(driver))
        return driver
    }

    override suspend fun findById(id: String): Driver? =
        collection.findOne(DriverDocument::_id eq id)?.toDomain()

    override suspend fun findByAgency(agencyId: String): List<Driver> =
        collection.find(DriverDocument::agencyId eq agencyId)
            .toList()
            .map { it.toDomain() }

    override suspend fun updateStatus(driverId: String, status: DriverStatus) {
        collection.updateOne(
            DriverDocument::_id eq driverId,
            setValue(DriverDocument::status, status)
        )
    }
}
