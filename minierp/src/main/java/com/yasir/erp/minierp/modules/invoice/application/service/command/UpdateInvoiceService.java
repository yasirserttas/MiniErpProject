package com.yasir.erp.minierp.modules.invoice.application.service.command;

import com.yasir.erp.minierp.modules.invoice.domain.port.inbound.command.UpdateInvoiceUseCase;
import com.yasir.erp.minierp.modules.invoice.application.dto.InvoiceDto;
import com.yasir.erp.minierp.modules.invoice.application.dto.request.UpdateInvoiceDtoRequest;
import com.yasir.erp.minierp.modules.invoice.application.converter.InvoiceConverter;
import com.yasir.erp.minierp.modules.invoice.domain.port.outbound.query.InvoiceActiveQueryPort;
import com.yasir.erp.minierp.modules.invoice.domain.port.outbound.command.InvoiceCommandPort;
import com.yasir.erp.minierp.modules.invoice.domain.model.Invoice;
import com.yasir.erp.minierp.modules.order.domain.model.Order;
import com.yasir.erp.minierp.modules.orderItem.domain.model.OrderItem;
import com.yasir.erp.minierp.modules.invoice.application.exception.InvoiceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UpdateInvoiceService implements UpdateInvoiceUseCase {

    private final InvoiceConverter invoiceConverter;
    private final InvoiceCommandPort invoiceCommandPort;
    private final InvoiceActiveQueryPort invoiceActiveQueryPort;
    private final InvoicePdfService invoicePdfService;

    public UpdateInvoiceService(InvoiceConverter invoiceConverter,
                                InvoiceCommandPort invoiceCommandPort,
                                InvoiceActiveQueryPort invoiceActiveQueryPort,
                                InvoicePdfService invoicePdfService) {
        this.invoiceConverter = invoiceConverter;
        this.invoiceCommandPort = invoiceCommandPort;
        this.invoiceActiveQueryPort = invoiceActiveQueryPort;
        this.invoicePdfService = invoicePdfService;
    }

    @Override
    public InvoiceDto updateInvoice(UpdateInvoiceDtoRequest req) {
        Invoice existing = invoiceActiveQueryPort.findByIdAndActive(req.getId(), true)
                .orElseThrow(() -> new InvoiceNotFoundException(req.getId()));

        Order order = existing.getOrder();
        BigDecimal total = calcTotal(order.getOrderItems());
        BigDecimal tax = calcTax(order.getOrderItems());
        BigDecimal finalAmount = total.add(tax);

        Invoice updated = new Invoice(
                existing.getId(),
                existing.getInvoiceNumber(),
                existing.getUser(),
                existing.getCustomer(),
                order,
                existing.getCreatedAt(),
                LocalDateTime.now(),
                req.getInvoiceDate(),
                total,
                tax,
                finalAmount,
                existing.getIssuedBy(),
                existing.getReceivedBy(),
                true
        );

        Invoice saved = invoiceCommandPort.save(updated);
        InvoiceDto dto = invoiceConverter.convertToInvoiceDto(saved);
        invoicePdfService.generateInvoicePdf(dto);
        return dto;
    }

    private BigDecimal calcTotal(Set<OrderItem> items) {
        return items.stream()
                .map(i -> i.getUnitPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calcTax(Set<OrderItem> items) {
        return items.stream()
                .map(OrderItem::getTaxAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
