package com.yasir.erp.minierp.modules.address.application.dto

import com.yasir.erp.minierp.modules.user.application.dto.UserAddressDto

data class AddressUserDto(
    val id: String,
    val street: String,
    val district: String,
    val city: String,
    val country: String,
    val postalCode: String,
    val user: UserAddressDto,
)
