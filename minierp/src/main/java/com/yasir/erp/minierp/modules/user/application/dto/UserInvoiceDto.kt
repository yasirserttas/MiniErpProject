package com.yasir.erp.minierp.modules.user.application.dto

import java.util.UUID

data class UserInvoiceDto(
    val id: UUID,
    val name: String,
    val surName: String,
    val email: String,
    val active: Boolean
)
