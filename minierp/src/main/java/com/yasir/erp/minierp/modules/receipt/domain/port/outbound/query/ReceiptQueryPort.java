package com.yasir.erp.minierp.modules.receipt.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.receipt.domain.model.Receipt;

import java.util.Optional;

public interface ReceiptQueryPort {
    Optional<Receipt> findByIdAndActive(String id, boolean active);
    Optional<Receipt> findByReceiptNumberAndActive(String receiptNumber, boolean active);
    Optional<Receipt> findByPaymentIdAndActive(String paymentId, boolean active);
}