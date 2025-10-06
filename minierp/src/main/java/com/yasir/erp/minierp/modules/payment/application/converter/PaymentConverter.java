package com.yasir.erp.minierp.modules.payment.application.converter;

import com.yasir.erp.minierp.modules.invoice.application.converter.InvoicePaymentConverter;
import com.yasir.erp.minierp.modules.payment.application.dto.PaymentDto;
import com.yasir.erp.minierp.modules.payment.domain.model.Payment;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PaymentConverter {

    private final InvoicePaymentConverter invoicePaymentConverter;

    public PaymentConverter(InvoicePaymentConverter invoicePaymentConverter) {
        this.invoicePaymentConverter = invoicePaymentConverter;
    }

    public PaymentDto convertToPaymentDto(Payment payment){

        return new PaymentDto(
                payment.getId(),
                invoicePaymentConverter.convertToInvoicePaymentDto(payment.getInvoice()),
                payment.getAmount(),
                payment.getMethod(),
                payment.getPaidAt(),
                payment.getIssueDate(),
                payment.getDueDate(),
                payment.getNote(),
                payment.getCreatedAt(),
                payment.getUpdatedAt()
        );
    }

    public Set<PaymentDto> convertToPaymentSetDto (Set<Payment> payments){
        return payments.stream().map(this::convertToPaymentDto).collect(Collectors.toSet());
    }
}
