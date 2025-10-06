package com.yasir.erp.minierp.modules.supplier.application.dto.request

data class CreateSupplierDtoRequest(
    val name: String,
    val taxNumber: String,
    val phone: String,
    val email: String
)
