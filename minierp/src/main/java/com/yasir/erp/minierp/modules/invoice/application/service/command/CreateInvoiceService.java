package com.yasir.erp.minierp.modules.invoice.application.service.command;

import com.yasir.erp.minierp.modules.customer.application.exception.CustomerNotFoundException;
import com.yasir.erp.minierp.modules.invoice.domain.model.Invoice;
import com.yasir.erp.minierp.modules.invoice.domain.port.inbound.command.CreateInvoiceUseCase;
import com.yasir.erp.minierp.modules.invoice.application.dto.InvoiceDto;
import com.yasir.erp.minierp.modules.invoice.application.dto.request.CreateInvoiceDtoRequest;
import com.yasir.erp.minierp.modules.invoice.application.converter.InvoiceConverter;
import com.yasir.erp.minierp.modules.invoice.domain.port.outbound.command.InvoiceCommandPort;
import com.yasir.erp.minierp.modules.order.application.exception.OrderNotFoundException;
import com.yasir.erp.minierp.modules.user.application.exception.UserNotFoundException;
import com.yasir.erp.minierp.modules.user.domain.port.outbound.query.UserByIdQueryPort;
import com.yasir.erp.minierp.modules.customer.domain.port.outbound.query.CustomerActiveQueryPort;
import com.yasir.erp.minierp.modules.order.domain.port.outbound.query.OrderActiveQueryPort;
import com.yasir.erp.minierp.modules.user.domain.model.User;
import com.yasir.erp.minierp.modules.customer.domain.model.Customer;
import com.yasir.erp.minierp.modules.order.domain.model.Order;
import com.yasir.erp.minierp.modules.orderItem.domain.model.OrderItem;
import com.yasir.erp.minierp.common.generator.UlidGenerator;
import com.yasir.erp.minierp.common.generator.ShortIdGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class CreateInvoiceService implements CreateInvoiceUseCase {

    private final InvoiceCommandPort invoiceCommandPort;
    private final InvoiceConverter invoiceConverter;
    private final UserByIdQueryPort userByIdQueryPort;
    private final CustomerActiveQueryPort customerActiveQueryPort;
    private final OrderActiveQueryPort orderActiveQueryPort;
    private final InvoicePdfService invoicePdfService;

    public CreateInvoiceService(InvoiceCommandPort invoiceCommandPort,
                                InvoiceConverter invoiceConverter,
                                UserByIdQueryPort userByIdQueryPort,
                                CustomerActiveQueryPort customerActiveQueryPort,
                                OrderActiveQueryPort orderActiveQueryPort,
                                InvoicePdfService invoicePdfService) {
        this.invoiceCommandPort = invoiceCommandPort;
        this.invoiceConverter = invoiceConverter;
        this.userByIdQueryPort = userByIdQueryPort;
        this.customerActiveQueryPort = customerActiveQueryPort;
        this.orderActiveQueryPort = orderActiveQueryPort;
        this.invoicePdfService = invoicePdfService;
    }

    @Override
    public InvoiceDto addInvoice(CreateInvoiceDtoRequest createInvoiceDtoRequest) {

        User user = userByIdQueryPort.findById(createInvoiceDtoRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundException(createInvoiceDtoRequest.getUserId()));

        Customer customer = customerActiveQueryPort
                .findByIdAndActive(createInvoiceDtoRequest.getCustomerId(), true)
                .orElseThrow(() -> new CustomerNotFoundException
                        (createInvoiceDtoRequest.getCustomerId()));

        Order order = orderActiveQueryPort
                .findByIdAndActive(createInvoiceDtoRequest.getOrderId(), true)
                .orElseThrow(() -> new OrderNotFoundException(createInvoiceDtoRequest.getOrderId()));

        if (!order.getCustomer().getId().equals(customer.getId())) {
            throw new IllegalStateException("Siparişin müşterisi ile fatura müşterisi uyuşmuyor.");
        }

        String invoiceNumber = ShortIdGenerator.generate(10);

        BigDecimal total = calcTotal(order.getOrderItems());
        BigDecimal tax = calcTax(order.getOrderItems());
        BigDecimal finalAmount = total.add(tax);

        Invoice invoice = new com.yasir.erp.minierp.modules.invoice.domain.model.Invoice(
                UlidGenerator.generate(),
                invoiceNumber,
                user,
                customer,
                order,
                LocalDateTime.now(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                total,
                tax,
                finalAmount,
                user.getCompanyName(),
                customer.getCompanyName(),
                true
        );

        Invoice saved = invoiceCommandPort.save(invoice);
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
