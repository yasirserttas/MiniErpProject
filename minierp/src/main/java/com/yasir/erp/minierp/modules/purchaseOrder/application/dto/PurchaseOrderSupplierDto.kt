package com.yasir.erp.minierp.modules.purchaseOrder.application.dto

import com.yasir.erp.minierp.modules.purchaseItem.application.dto.PurchaseItemPurchaseOrderDto
import com.yasir.erp.minierp.modules.purchaseOrder.domain.model.PurchaseOrderStatus
import java.math.BigDecimal

data class PurchaseOrderSupplierDto(
    val id: String,
    val items: Set<PurchaseItemPurchaseOrderDto>,
    val status: PurchaseOrderStatus,
    val active: Boolean,
    val totalAmount: BigDecimal
)
