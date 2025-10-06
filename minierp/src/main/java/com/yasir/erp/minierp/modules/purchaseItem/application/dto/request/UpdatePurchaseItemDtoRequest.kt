package com.yasir.erp.minierp.modules.purchaseItem.application.dto.request

import java.math.BigDecimal

data class UpdatePurchaseItemDtoRequest(

    val id: String,
    val quantity: Int,
    val purchasePrice: BigDecimal,
    val taxRate: BigDecimal
)
