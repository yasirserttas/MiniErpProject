package com.yasir.erp.minierp.modules.order.domain.port.inbound.command;

public interface RecalculateTotalAmountUseCase {
    void recalculateTotalAmount(String orderId);
}
