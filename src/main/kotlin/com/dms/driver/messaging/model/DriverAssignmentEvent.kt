package com.dms.driver.messaging.model

import kotlinx.serialization.Serializable

@Serializable
data class DriverAssignmentEvent(
    val assignmentId: String,
    val orderId: String,
    val driverId: String,
    val slot: String,
    val timestamp: String
)