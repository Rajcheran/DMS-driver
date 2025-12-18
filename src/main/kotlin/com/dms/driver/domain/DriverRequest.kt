package com.dms.driver.domain

import kotlinx.serialization.Serializable

@Serializable
data class CreateDriverRequest(
    val id: String,
    val agencyId: String,
    val name: String,
    val phone: String,
    val pincode: String,
    val areaCode: String? = null
)
