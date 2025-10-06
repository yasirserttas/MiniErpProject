package com.yasir.erp.minierp.modules.purchaseOrder.application.dto

import com.yasir.erp.minierp.modules.purchaseItem.application.dto.PurchaseItemPurchaseOrderDto
import com.yasir.erp.minierp.modules.supplier.application.dto.SupplierPurchaseOrderDto
import com.yasir.erp.minierp.modules.purchaseOrder.domain.model.PurchaseOrderStatus
import java.math.BigDecimal
import java.time.LocalDateTime

data class PurchaseOrderDto(
    val id: String,
    val supplier: SupplierPurchaseOrderDto,
    val items: Set<PurchaseItemPurchaseOrderDto>,
    val status: PurchaseOrderStatus,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val active: Boolean,
    val totalAmount: BigDecimal
)