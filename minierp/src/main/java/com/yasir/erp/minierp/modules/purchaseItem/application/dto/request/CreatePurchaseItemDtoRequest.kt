package com.yasir.erp.minierp.modules.purchaseItem.application.dto.request

import java.math.BigDecimal

data class CreatePurchaseItemDtoRequest(

    val productId: String,
    val purchaseOrderId: String,
    val quantity: Int,
    val purchasePrice: BigDecimal,
    val taxRate: BigDecimal
)
