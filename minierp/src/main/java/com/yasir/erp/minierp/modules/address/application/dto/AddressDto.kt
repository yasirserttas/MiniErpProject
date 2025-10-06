package com.yasir.erp.minierp.modules.address.application.dto

import com.yasir.erp.minierp.modules.customer.application.dto.CustomerAddressDto
import com.yasir.erp.minierp.modules.supplier.application.dto.SupplierAddressDto
import com.yasir.erp.minierp.modules.user.application.dto.UserAddressDto

data class AddressDto(
    val id: String,
    val street: String,
    val district: String,
    val city: String,
    val country: String,
    val postalCode: String,
    val user: UserAddressDto?,
    val customer: CustomerAddressDto?,
    val supplier: SupplierAddressDto?
)