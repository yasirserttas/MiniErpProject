package com.yasir.erp.minierp.modules.receipt.application.dto

import com.yasir.erp.minierp.modules.payment.application.dto.PaymentDto
import java.time.LocalDateTime

data class ReceiptDto(
    val id: String,
    val payment: PaymentDto, // sade DTO
    val receiptNumber: String,
    val issuedBy: String,
    val receivedBy: String,
    val receiptDate: LocalDateTime,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val active: Boolean
)
