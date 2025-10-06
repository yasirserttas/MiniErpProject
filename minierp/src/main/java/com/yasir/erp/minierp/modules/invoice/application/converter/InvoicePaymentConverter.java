package com.yasir.erp.minierp.modules.invoice.application.converter;

import com.yasir.erp.minierp.modules.invoice.application.dto.InvoicePaymentDto;
import com.yasir.erp.minierp.modules.invoice.domain.model.Invoice;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class InvoicePaymentConverter {

    public InvoicePaymentDto convertToInvoicePaymentDto(Invoice invoice) {
        return new InvoicePaymentDto(
                invoice.getId(),
                invoice.getInvoiceNumber(),
                invoice.getInvoiceDate(),
                invoice.getFinalAmount(),
                invoice.getActive()
        );
    }

    public Set<InvoicePaymentDto> convertToSetInvoicePaymentDto(Set<Invoice> invoices) {
        return invoices.stream()
                .map(this::convertToInvoicePaymentDto)
                .collect(Collectors.toSet());
    }
}
