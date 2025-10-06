package com.yasir.erp.minierp.modules.purchaseOrder.application.dto.request

import com.yasir.erp.minierp.modules.purchaseItem.application.dto.request.CreatePurchaseItemDtoRequest

data class CreatePurchaseOrderDtoRequest(
    val supplierId: String,
    val items: Set<CreatePurchaseItemDtoRequest>
)
