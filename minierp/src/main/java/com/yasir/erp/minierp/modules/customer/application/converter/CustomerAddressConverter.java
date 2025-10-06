package com.yasir.erp.minierp.modules.customer.application.converter;

import com.yasir.erp.minierp.modules.customer.application.dto.CustomerAddressDto;
import com.yasir.erp.minierp.modules.customer.domain.model.Customer;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CustomerAddressConverter {

    public CustomerAddressDto convertToCustomerAddress(Customer customer){
        return new CustomerAddressDto(
                customer.getId(),
                customer.getName(),
                customer.getSurName(),
                customer.getCompanyName()
        );
    }

    public Set<CustomerAddressDto> convertToCustomerAddress(Set<Customer> customers){
        return customers.stream().map(this::convertToCustomerAddress).collect(Collectors.toSet());
    }

}
