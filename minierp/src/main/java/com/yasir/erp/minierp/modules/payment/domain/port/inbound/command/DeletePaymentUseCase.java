package com.yasir.erp.minierp.modules.payment.domain.port.inbound.command;

public interface DeletePaymentUseCase {
    void deleteById(String paymentId);
}