package com.yasir.erp.minierp.modules.user.application.dto

import java.util.UUID

data class UserOrderDto(
    val id: UUID,
    val name: String,
    val surName: String,
    val username: String,
    val email: String,
    val companyName: String?,
    val active: Boolean
)
