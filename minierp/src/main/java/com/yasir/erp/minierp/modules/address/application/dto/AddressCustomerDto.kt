package com.yasir.erp.minierp.modules.address.application.dto

import com.yasir.erp.minierp.modules.customer.application.dto.CustomerAddressDto

data class AddressCustomerDto(
    val id: String,
    val street: String,
    val district: String,
    val city: String,
    val country: String,
    val postalCode: String,
    val customer: CustomerAddressDto
)
