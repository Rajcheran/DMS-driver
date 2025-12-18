package com.dms.driver.domain

import kotlinx.serialization.Serializable

@Serializable
data class AssignmentRequest(
    val id: String,
    val orderId: String,
    val driverId: String,
    val slot: String
)