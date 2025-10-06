package com.yasir.erp.minierp.modules.invoice.application.converter;

import com.yasir.erp.minierp.modules.invoice.application.dto.InvoiceUserDto;
import com.yasir.erp.minierp.modules.invoice.domain.model.Invoice;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class InvoiceUserConverter {

    public InvoiceUserDto convertToInvoiceUserDto(Invoice invoice) {
        return new InvoiceUserDto(
                invoice.getId(),
                invoice.getInvoiceNumber(),
                invoice.getInvoiceDate(),
                invoice.getTotalAmount(),
                invoice.getFinalAmount(),
                invoice.getActive()
        );
    }

    public Set<InvoiceUserDto> convertToSetInvoiceUserDto(Set<Invoice> invoices) {
        return invoices.stream()
                .map(this::convertToInvoiceUserDto)
                .collect(Collectors.toSet());
    }
}
