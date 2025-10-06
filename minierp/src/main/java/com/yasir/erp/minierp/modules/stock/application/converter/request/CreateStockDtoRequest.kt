package com.yasir.erp.minierp.modules.stock.application.converter.request

data class CreateStockDtoRequest(
    val productId: String,
    val quantity: Int
)
