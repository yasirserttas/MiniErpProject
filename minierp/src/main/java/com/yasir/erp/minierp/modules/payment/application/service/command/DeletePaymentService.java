package com.yasir.erp.minierp.modules.payment.application.service.command;

import com.yasir.erp.minierp.modules.payment.domain.model.Payment;
import com.yasir.erp.minierp.modules.payment.domain.port.inbound.command.DeletePaymentUseCase;
import com.yasir.erp.minierp.modules.payment.domain.port.outbound.command.PaymentCommandPort;
import com.yasir.erp.minierp.modules.payment.domain.port.outbound.query.PaymentQueryPort;
import com.yasir.erp.minierp.modules.payment.application.exception.PaymentNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeletePaymentService implements DeletePaymentUseCase {

    private final PaymentQueryPort paymentQueryPort;
    private final PaymentCommandPort paymentCommandPort;

    public DeletePaymentService(PaymentQueryPort paymentQueryPort,
                                PaymentCommandPort paymentCommandPort) {
        this.paymentQueryPort = paymentQueryPort;
        this.paymentCommandPort = paymentCommandPort;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteById(String paymentId) {
        Payment payment = paymentQueryPort.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException(paymentId));
        paymentCommandPort.delete(payment);
    }
}
