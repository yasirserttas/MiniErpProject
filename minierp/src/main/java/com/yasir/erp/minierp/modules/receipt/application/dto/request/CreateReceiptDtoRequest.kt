package com.yasir.erp.minierp.modules.receipt.application.dto.request

import java.time.LocalDateTime

data class CreateReceiptDtoRequest(
    val bankAccountId: String,
    val paymentId: String,
    val issuedBy: String,
    val receivedBy: String,
    val receiptDate: LocalDateTime?
)

