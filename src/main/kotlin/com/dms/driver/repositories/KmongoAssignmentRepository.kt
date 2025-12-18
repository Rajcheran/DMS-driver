package com.dms.driver.repositories

import com.dms.driver.domain.Assignment
import com.dms.driver.repositories.AssignmentRepository
import kotlinx.coroutines.flow.toList
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.eq
import org.litote.kmongo.getCollection

data class AssignmentEntity(
    val _id: String,
    val orderId: String,
    val driverId: String,
    val slot: String,
    val status: String,
    val createdAt: Long
) {
    fun toDomain() = Assignment(_id, orderId, driverId, slot, com.dms.driver.domain.AssignmentStatus.valueOf(status), createdAt)
    companion object {
        fun fromDomain(d: Assignment) = AssignmentEntity(d.id, d.orderId, d.driverId, d.slot, d.status.name, d.createdAt)
    }
}

class KmongoAssignmentRepository(private val db: CoroutineDatabase) : AssignmentRepository {
    private val col: CoroutineCollection<AssignmentEntity> = db.getCollection()

    override suspend fun save(assignment: Assignment): Assignment {
        val entity = AssignmentEntity.fromDomain(assignment)
        col.insertOne(entity)
        return entity.toDomain()
    }

    override suspend fun findByDriverId(driverId: String): List<Assignment> =
        col.find(AssignmentEntity::driverId eq driverId).toList().map { it.toDomain() }
}