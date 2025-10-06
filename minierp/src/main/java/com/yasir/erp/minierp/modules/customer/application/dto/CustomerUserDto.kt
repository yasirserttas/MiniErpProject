package com.yasir.erp.minierp.modules.customer.application.dto

import java.util.UUID

data class CustomerUserDto(
    val id: UUID,
    val name: String,
    val surName: String,
    val companyName: String,
    val phoneNumber: String,
    val email: String,
    val active: Boolean
)
