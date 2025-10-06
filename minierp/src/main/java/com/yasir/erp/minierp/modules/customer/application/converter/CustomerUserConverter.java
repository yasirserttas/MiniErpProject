package com.yasir.erp.minierp.modules.customer.application.converter;

import com.yasir.erp.minierp.modules.customer.application.dto.CustomerUserDto;
import com.yasir.erp.minierp.modules.customer.domain.model.Customer;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CustomerUserConverter {

    public CustomerUserDto convertToCustomerUserDto(Customer customer) {
        return new CustomerUserDto(
                customer.getId(),
                customer.getName(),
                customer.getSurName(),
                customer.getCompanyName(),
                customer.getPhoneNumber(),
                customer.getEmail(),
                customer.getActive()
        );
    }

    public Set<CustomerUserDto> convertToSetCustomerUserDto(Set<Customer> customers) {
        return customers.stream()
                .map(this::convertToCustomerUserDto)
                .collect(Collectors.toSet());
    }
}
