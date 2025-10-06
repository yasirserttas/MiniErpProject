package com.yasir.erp.minierp.modules.customer.application.converter;

import com.yasir.erp.minierp.modules.customer.application.dto.CustomerInvoiceDto;
import com.yasir.erp.minierp.modules.customer.domain.model.Customer;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CustomerInvoiceConverter {
    public CustomerInvoiceDto convertToCustomerInvoiceDto(Customer customer) {
        return new CustomerInvoiceDto(
                customer.getId(),
                customer.getName(),
                customer.getSurName(),
                customer.getCompanyName(),
                customer.getTaxNumber(),
                customer.getEmail(),
                customer.getActive()
        );
    }

    public Set<CustomerInvoiceDto> convertToSetCustomerInvoiceDto(Set<Customer> customers) {
        return customers.stream()
                .map(this::convertToCustomerInvoiceDto)
                .collect(Collectors.toSet());
    }
}
