package com.yasir.erp.minierp.modules.address.application.dto.request.supplier

data class CreateSupplierAddressDtoRequest(
    val supplierId: String,
    val street: String,
    val district: String,
    val city: String,
    val country: String,
    val postalCode: String
)
