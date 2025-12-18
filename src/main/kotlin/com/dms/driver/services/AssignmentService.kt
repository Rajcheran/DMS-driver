package com.dms.driver.services

import com.dms.driver.domain.Assignment
import com.dms.driver.repositories.AssignmentRepository
import com.dms.driver.repositories.EventPublisher
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class AssignmentService(
    private val repo: AssignmentRepository,
    private val publisher: EventPublisher
) {
    suspend fun create(assignment: Assignment): Assignment {
        val saved = repo.save(assignment)
        // publish minimal event as JSON string (simple)
        val payload = Json.encodeToString(saved)
        publisher.publish("assignments.created", saved.id, payload)
        return saved
    }

    suspend fun listForDriver(driverId: String): List<Assignment> =
        repo.findByDriverId(driverId)
}