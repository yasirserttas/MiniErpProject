package com.yasir.erp.minierp.modules.customer.application.dto

import java.util.UUID

data class CustomerAddressDto(
    val id: UUID,
    val name: String?,
    val surName: String? = null,
    val companyName: String? = null
)
