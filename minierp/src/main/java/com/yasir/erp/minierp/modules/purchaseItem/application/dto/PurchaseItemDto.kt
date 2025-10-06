package com.yasir.erp.minierp.modules.purchaseItem.application.dto

import com.yasir.erp.minierp.modules.product.application.dto.ProductPurchaseItemDto
import java.math.BigDecimal

data class PurchaseItemDto(
    val id: String,
    val product: ProductPurchaseItemDto,
    val quantity: Int,
    val purchasePrice: BigDecimal,
    val taxRate: BigDecimal,
    val taxAmount: BigDecimal,
    val totalCost: BigDecimal
)
