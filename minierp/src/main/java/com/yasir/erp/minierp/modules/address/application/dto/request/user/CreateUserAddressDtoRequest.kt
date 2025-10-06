package com.yasir.erp.minierp.modules.address.application.dto.request.user

import java.util.UUID

data class CreateUserAddressDtoRequest(
    val userId: UUID,
    val street: String,
    val district: String,
    val city: String,
    val country: String,
    val postalCode: String
)
