package com.yasir.erp.minierp.modules.payment.application.service.command;

import com.yasir.erp.minierp.modules.payment.application.converter.PaymentConverter;
import com.yasir.erp.minierp.modules.payment.application.dto.PaymentDto;
import com.yasir.erp.minierp.modules.payment.application.dto.request.UpdatePaymentDtoRequest;
import com.yasir.erp.minierp.modules.payment.domain.model.Payment;
import com.yasir.erp.minierp.modules.payment.domain.port.inbound.command.UpdatePaymentUseCase;
import com.yasir.erp.minierp.modules.payment.domain.port.outbound.command.PaymentCommandPort;
import com.yasir.erp.minierp.modules.payment.domain.port.outbound.query.PaymentQueryPort;
import com.yasir.erp.minierp.modules.payment.application.exception.PaymentNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UpdatePaymentService implements UpdatePaymentUseCase {

    private final PaymentQueryPort paymentQueryPort;
    private final PaymentCommandPort paymentCommandPort;
    private final PaymentConverter paymentConverter;

    public UpdatePaymentService(PaymentQueryPort paymentQueryPort,
                                PaymentCommandPort paymentCommandPort,
                                PaymentConverter paymentConverter) {
        this.paymentQueryPort = paymentQueryPort;
        this.paymentCommandPort = paymentCommandPort;
        this.paymentConverter = paymentConverter;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public PaymentDto updatePayment(UpdatePaymentDtoRequest updatePaymentDtoRequest) {
        Payment existing = paymentQueryPort.findById(updatePaymentDtoRequest.getId())
                .orElseThrow(() ->
                        new PaymentNotFoundException(updatePaymentDtoRequest.getId()));

        Payment updated = new Payment(
                existing.getId(),
                existing.getInvoice(),
                updatePaymentDtoRequest.getAmount(),
                updatePaymentDtoRequest.getMethod(),
                updatePaymentDtoRequest.getPaidAt(),
                updatePaymentDtoRequest.getIssueDate(),
                updatePaymentDtoRequest.getDueDate(),
                updatePaymentDtoRequest.getNote(),
                existing.getCreatedAt(),
                LocalDateTime.now()
        );

        return paymentConverter.convertToPaymentDto(paymentCommandPort.save(updated));
    }
}
