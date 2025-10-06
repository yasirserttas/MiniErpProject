package com.yasir.erp.minierp.modules.invoice.application.service.command;

import com.yasir.erp.minierp.modules.invoice.domain.port.inbound.command.DeactivateInvoiceUseCase;
import com.yasir.erp.minierp.modules.invoice.application.dto.InvoiceDto;
import com.yasir.erp.minierp.modules.invoice.application.converter.InvoiceConverter;
import com.yasir.erp.minierp.modules.invoice.domain.port.outbound.query.InvoiceActiveQueryPort;
import com.yasir.erp.minierp.modules.invoice.domain.port.outbound.command.InvoiceCommandPort;
import com.yasir.erp.minierp.modules.invoice.domain.model.Invoice;
import com.yasir.erp.minierp.modules.invoice.application.exception.InvoiceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import java.time.LocalDateTime;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class DeactivateInvoiceService implements DeactivateInvoiceUseCase {

    private final InvoiceCommandPort commandPort;
    private final InvoiceActiveQueryPort activeQueryPort;
    private final InvoiceConverter converter;
    private final InvoicePdfService invoicePdfService;

    public DeactivateInvoiceService(InvoiceCommandPort commandPort,
                                    InvoiceActiveQueryPort activeQueryPort,
                                    InvoiceConverter converter,
                                    InvoicePdfService invoicePdfService) {
        this.commandPort = commandPort;
        this.activeQueryPort = activeQueryPort;
        this.converter = converter;
        this.invoicePdfService = invoicePdfService;
    }

    @Override
    public InvoiceDto deactivateInvoice(String invoiceId) {
        Invoice existing = activeQueryPort.findByIdAndActive(invoiceId, true)
                .orElseThrow(() -> new InvoiceNotFoundException(invoiceId));

        Invoice updated = new Invoice(
                existing.getId(),
                existing.getInvoiceNumber(),
                existing.getUser(),
                existing.getCustomer(),
                existing.getOrder(),
                existing.getCreatedAt(),
                LocalDateTime.now(),
                existing.getInvoiceDate(),
                existing.getTotalAmount(),
                existing.getTaxAmount(),
                existing.getFinalAmount(),
                existing.getIssuedBy(),
                existing.getReceivedBy(),
                false
        );

        Invoice saved = commandPort.save(updated);
        InvoiceDto dto = converter.convertToInvoiceDto(saved);
        invoicePdfService.generateInvoicePdf(dto);
        return dto;
    }
}
