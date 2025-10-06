package com.yasir.erp.minierp.modules.purchaseOrder.application.dto

import com.yasir.erp.minierp.modules.purchaseOrder.domain.model.PurchaseOrderStatus
import java.math.BigDecimal
import java.time.LocalDateTime

data class PurchaseOrderPurchaseItemDto(
    val id: String,
    val purchaseOrderStatus: PurchaseOrderStatus,
    val createdAt: LocalDateTime,
    val totalAmount: BigDecimal,
    val active: Boolean
)
