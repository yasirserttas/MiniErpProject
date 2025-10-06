package com.yasir.erp.minierp.modules.purchaseOrder.application.dto.request

import com.yasir.erp.minierp.modules.purchaseOrder.domain.model.PurchaseOrderStatus

data class UpdatePurchaseOrderDtoRequest(
    val id: String,
    val status: PurchaseOrderStatus
)
