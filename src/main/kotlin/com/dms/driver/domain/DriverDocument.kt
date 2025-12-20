package com.dms.driver.domain


data class DriverDocument(
    val _id: String,
    val agencyId: String,
    val name: String,
    val phone: String,
    val pincode: String,
    val status: String
) {
    fun toDomain(): Driver =
        Driver(
            id = _id,
            agencyId = agencyId,
            name = name,
            phone = phone,
            pincode = pincode,
            status = DriverStatus.valueOf(status)
        )

    companion object {
        fun fromDomain(d: Driver): DriverDocument =
            DriverDocument(
                _id = d.id,
                agencyId = d.agencyId,
                name = d.name,
                phone = d.phone,
                pincode = d.pincode,
                status = d.status.name
            )
    }
}