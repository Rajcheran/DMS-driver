package com.dms.driver.domain

data class DriverOperationalState(
    val status: DriverStatus,
    val assignmentCount: Int
)
