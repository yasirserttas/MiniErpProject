package com.yasir.erp.minierp.modules.supplier.application.dto

data class SupplierPurchaseOrderDto(
    val id: String,
    val name: String,
    val taxNumber: String,
    val phone: String,
    val email: String,
    val active: Boolean
)
