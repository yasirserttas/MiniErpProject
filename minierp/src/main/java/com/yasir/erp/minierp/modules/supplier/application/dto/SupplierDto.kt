package com.yasir.erp.minierp.modules.supplier.application.dto


import com.yasir.erp.minierp.modules.address.application.dto.AddressSupplierDto
import com.yasir.erp.minierp.modules.purchaseOrder.application.dto.PurchaseOrderSupplierDto
import java.time.LocalDateTime

data class SupplierDto(
    val id: String,
    val name: String,
    val taxNumber: String,
    val phone: String,
    val email: String,
    val purchaseOrders: Set<PurchaseOrderSupplierDto>?,
    val addresses : Set<AddressSupplierDto>?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val active: Boolean
)
