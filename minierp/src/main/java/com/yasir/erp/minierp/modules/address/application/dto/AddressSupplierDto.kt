package com.yasir.erp.minierp.modules.address.application.dto

import com.yasir.erp.minierp.modules.supplier.application.dto.SupplierAddressDto

data class AddressSupplierDto(
    val id: String,
    val street: String,
    val district: String,
    val city: String,
    val country: String,
    val postalCode: String,
    val supplier: SupplierAddressDto
)
