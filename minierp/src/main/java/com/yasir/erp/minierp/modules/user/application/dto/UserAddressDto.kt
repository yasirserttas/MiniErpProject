package com.yasir.erp.minierp.modules.user.application.dto

import java.util.UUID

data class UserAddressDto(
    val id: UUID,
    val username: String?,
    val companyName: String,
    val name: String? = null,
    val surName: String? = null
)
