package com.dms.driver.domain

import kotlinx.serialization.Serializable

@Serializable
data class Assignment(
    val id: String,
    val orderId: String,
    val driverId: String,
    val slot: String,
    val status: AssignmentStatus = AssignmentStatus.PENDING,
    val createdAt: Long = System.currentTimeMillis()
)

enum class AssignmentStatus { PENDING, OUT_FOR_DELIVERY, DELIVERED, FAILED }