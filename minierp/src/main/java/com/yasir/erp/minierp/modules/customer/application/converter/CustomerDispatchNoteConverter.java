package com.yasir.erp.minierp.modules.customer.application.converter;

import com.yasir.erp.minierp.modules.customer.application.dto.CustomerDispatchNoteDto;
import com.yasir.erp.minierp.modules.customer.domain.model.Customer;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CustomerDispatchNoteConverter {
    public CustomerDispatchNoteDto convertToCustomerDispatchNoteDto(Customer customer) {
        return new CustomerDispatchNoteDto(
                customer.getId(),
                customer.getName(),
                customer.getSurName(),
                customer.getCompanyName(),
                customer.getPhoneNumber(),
                customer.getEmail(),
                customer.getActive()
        );
    }

    public Set<CustomerDispatchNoteDto> convertToSetCustomerDispatchNoteDto(Set<Customer> customers) {
        return customers.stream()
                .map(this::convertToCustomerDispatchNoteDto)
                .collect(Collectors.toSet());
    }
}
