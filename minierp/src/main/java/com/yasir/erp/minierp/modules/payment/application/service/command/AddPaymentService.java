package com.yasir.erp.minierp.modules.payment.application.service.command;

import com.yasir.erp.minierp.common.generator.UlidGenerator;
import com.yasir.erp.minierp.modules.invoice.domain.model.Invoice;
import com.yasir.erp.minierp.modules.invoice.domain.port.outbound.query.InvoiceActiveQueryPort;
import com.yasir.erp.minierp.modules.payment.application.converter.PaymentConverter;
import com.yasir.erp.minierp.modules.payment.application.dto.PaymentDto;
import com.yasir.erp.minierp.modules.payment.application.dto.request.CreatePaymentDtoRequest;
import com.yasir.erp.minierp.modules.payment.domain.model.Payment;
import com.yasir.erp.minierp.modules.payment.domain.port.inbound.command.AddPaymentUseCase;
import com.yasir.erp.minierp.modules.payment.domain.port.outbound.command.PaymentCommandPort;
import com.yasir.erp.minierp.modules.invoice.application.exception.InvoiceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AddPaymentService implements AddPaymentUseCase {

    private final PaymentCommandPort paymentCommandPort;
    private final PaymentConverter paymentConverter;
    private final InvoiceActiveQueryPort invoiceActiveQueryPort;

    public AddPaymentService(PaymentCommandPort paymentCommandPort,
                             PaymentConverter paymentConverter,
                             InvoiceActiveQueryPort invoiceActiveQueryPort) {
        this.paymentCommandPort = paymentCommandPort;
        this.paymentConverter = paymentConverter;
        this.invoiceActiveQueryPort = invoiceActiveQueryPort;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public PaymentDto addPayment(CreatePaymentDtoRequest createPaymentDtoRequest) {
        Invoice invoice =
                invoiceActiveQueryPort.findByIdAndActive
                                (createPaymentDtoRequest.getInvoiceId(), true)
                .orElseThrow(() -> new
                        InvoiceNotFoundException(createPaymentDtoRequest.getInvoiceId()));

        Payment payment = new Payment(
                UlidGenerator.generate(),
                invoice,
                createPaymentDtoRequest.getAmount(),
                createPaymentDtoRequest.getMethod(),
                createPaymentDtoRequest.getPaidAt(),
                createPaymentDtoRequest.getIssueDate(),
                createPaymentDtoRequest.getDueDate(),
                createPaymentDtoRequest.getNote(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        return paymentConverter.convertToPaymentDto(paymentCommandPort.save(payment));
    }
}
