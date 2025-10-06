package com.yasir.erp.minierp.modules.customer.application.converter;

import com.yasir.erp.minierp.modules.address.application.converter.AddressCustomerConverter;
import com.yasir.erp.minierp.modules.order.application.converter.OrderCustomerConverter;
import com.yasir.erp.minierp.modules.user.application.converter.UserCustomerConverter;
import com.yasir.erp.minierp.modules.customer.application.dto.CustomerDto;
import com.yasir.erp.minierp.modules.customer.domain.model.Customer;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CustomerConverter {

    private final UserCustomerConverter userCustomerConverter;
    private final OrderCustomerConverter orderCustomerConverter;
    private final AddressCustomerConverter addressCustomerConverter;

    public CustomerConverter(UserCustomerConverter userCustomerConverter,
                             OrderCustomerConverter orderCustomerConverter,
                             AddressCustomerConverter addressCustomerConverter) {
        this.userCustomerConverter = userCustomerConverter;
        this.orderCustomerConverter = orderCustomerConverter;
        this.addressCustomerConverter = addressCustomerConverter;
    }

    public CustomerDto convertToCustomer(Customer customer){

        return new CustomerDto(
                customer.getId(),
                customer.getName(),
                customer.getSurName(),
                customer.getCompanyName(),
                customer.getPhoneNumber(),
                customer.getEmail(),
                customer.getTaxNumber(),
                customer.getCreatedAt(),
                customer.getUpdateAt(),
                userCustomerConverter.
                        convertToUserCustomerDto(customer.getUser()),
                addressCustomerConverter.convertToSetAddressCustomer(customer.getAddresses()),
                orderCustomerConverter.
                        convertToSetOrderCustomerDto(customer.getOrders()),
                customer.getActive()
        );
    }

    public Set<CustomerDto>  convertToCustomerSet(Set<Customer> customers){

        return customers.stream().map(this::convertToCustomer).collect(Collectors.toSet());
    }

}
