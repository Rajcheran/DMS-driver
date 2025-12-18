package com.dms.driver.repositories

import com.dms.driver.domain.Assignment

interface AssignmentRepository {
    suspend fun save(assignment: Assignment): Assignment
    suspend fun findByDriverId(driverId: String): List<Assignment>
}