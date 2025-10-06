package com.yasir.erp.minierp.modules.customer.application.converter;

import com.yasir.erp.minierp.modules.customer.application.dto.CustomerOrderDto;
import com.yasir.erp.minierp.modules.customer.domain.model.Customer;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CustomerOrderConverter {

    public CustomerOrderDto convertToCustomerOrderDto(Customer customer){

        return new CustomerOrderDto(
                customer.getId(),
                customer.getName(),
                customer.getSurName(),
                customer.getCompanyName(),
                customer.getPhoneNumber(),
                customer.getEmail(),
                customer.getActive()
        );

    }

    public Set<CustomerOrderDto> convertToSetCustomerOrderDto(Set<Customer> customers){

        return customers.stream().map(this::convertToCustomerOrderDto).collect(Collectors.toSet());
    }

 }
