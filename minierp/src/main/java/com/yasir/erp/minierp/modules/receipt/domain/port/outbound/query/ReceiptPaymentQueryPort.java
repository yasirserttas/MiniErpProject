package com.yasir.erp.minierp.modules.receipt.domain.port.outbound.query;

import com.yasir.erp.minierp.modules.receipt.domain.model.Receipt;
import java.util.Set;

public interface ReceiptPaymentQueryPort {
    Set<Receipt> findAllByPaymentIdAndActive(String paymentId, boolean active);
}