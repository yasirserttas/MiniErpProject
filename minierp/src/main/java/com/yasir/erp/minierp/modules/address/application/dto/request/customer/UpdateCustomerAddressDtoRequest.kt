package com.yasir.erp.minierp.modules.address.application.dto.request.customer

data class UpdateCustomerAddressDtoRequest(
    val addressId: String,
    val customerId: String,
    val street: String,
    val district: String,
    val city: String,
    val country: String,
    val postalCode: String
)
