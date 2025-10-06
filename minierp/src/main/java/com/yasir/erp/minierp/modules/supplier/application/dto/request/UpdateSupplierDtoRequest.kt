package com.yasir.erp.minierp.modules.supplier.application.dto.request

data class UpdateSupplierDtoRequest(
    val id: String,
    val name: String,
    val taxNumber: String,
    val phone: String,
    val email: String
)
