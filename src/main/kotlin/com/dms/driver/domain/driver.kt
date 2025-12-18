package com.dms.driver.domain

import kotlinx.serialization.Serializable

@Serializable
data class Driver(
    val id: String,
    val agencyId: String,
    val name: String,
    val phone: String,
    val pincode: String,
    val areaCode: String? = null,
    val status: DriverStatus = DriverStatus.ACTIVE
)

enum class DriverStatus { ACTIVE, INACTIVE }