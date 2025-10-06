package com.yasir.erp.minierp.modules.address.application.dto.request.customer

import java.util.UUID


data class CreateCustomerAddressDtoRequest(
    val customerId: UUID,
    val street: String,
    val district: String,
    val city: String,
    val country: String,
    val postalCode: String
)