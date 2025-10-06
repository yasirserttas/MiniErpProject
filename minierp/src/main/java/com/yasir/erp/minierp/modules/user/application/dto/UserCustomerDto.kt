package com.yasir.erp.minierp.modules.user.application.dto

import java.util.UUID

data class UserCustomerDto(
    val id: UUID,
    val name: String,
    val surName: String,
    val username: String,
    val companyName: String,
    val email: String,
    val active: Boolean
)
