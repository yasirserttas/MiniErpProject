package com.yasir.erp.minierp.modules.invoice.application.converter;

import com.yasir.erp.minierp.modules.customer.application.converter.CustomerInvoiceConverter;
import com.yasir.erp.minierp.modules.order.application.converter.OrderInvoiceConverter;
import com.yasir.erp.minierp.modules.user.application.converter.UserInvoiceConverter;
import com.yasir.erp.minierp.modules.invoice.application.dto.InvoiceDto;
import com.yasir.erp.minierp.modules.invoice.domain.model.Invoice;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class InvoiceConverter {

    private final UserInvoiceConverter userInvoiceConverter;
    private final CustomerInvoiceConverter customerInvoiceConverter;
    private final OrderInvoiceConverter orderInvoiceConverter;

    public InvoiceConverter(UserInvoiceConverter userInvoiceConverter,
                            CustomerInvoiceConverter customerInvoiceConverter,
                            OrderInvoiceConverter orderInvoiceConverter) {
        this.userInvoiceConverter = userInvoiceConverter;
        this.customerInvoiceConverter = customerInvoiceConverter;
        this.orderInvoiceConverter = orderInvoiceConverter;
    }

    public InvoiceDto convertToInvoiceDto(Invoice invoice){

        return new InvoiceDto(
                invoice.getId(),
                invoice.getInvoiceNumber(),
                userInvoiceConverter.convertToUserInvoiceDto(invoice.getUser()),
                customerInvoiceConverter.convertToCustomerInvoiceDto(invoice.getCustomer()),
                orderInvoiceConverter.convertToOrderInvoiceDto(invoice.getOrder()),
                invoice.getCreatedAt(),
                invoice.getUpdatedAt(),
                invoice.getInvoiceDate(),
                invoice.getTotalAmount(),
                invoice.getTaxAmount(),
                invoice.getFinalAmount(),
                invoice.getIssuedBy(),
                invoice.getReceivedBy(),
                invoice.getActive()
        );
    }

    public Set<InvoiceDto> convertToInvoiceSetDto(Set<Invoice> invoices){
        return invoices.stream().map(this::convertToInvoiceDto).collect(Collectors.toSet());
    }

}
