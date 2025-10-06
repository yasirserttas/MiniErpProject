package com.yasir.erp.minierp.modules.address.application.converter;

import com.yasir.erp.minierp.modules.customer.application.converter.CustomerAddressConverter;
import com.yasir.erp.minierp.modules.address.application.dto.AddressCustomerDto;
import com.yasir.erp.minierp.modules.address.domain.model.Address;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AddressCustomerConverter {

    private final CustomerAddressConverter customerAddressConverter;

    public AddressCustomerConverter(CustomerAddressConverter customerAddressConverter) {
        this.customerAddressConverter = customerAddressConverter;
    }

    public AddressCustomerDto convertToAddressCustomer(Address address){
        return new AddressCustomerDto(
                address.getId(),
                address.getStreet(),
                address.getDistrict(),
                address.getCity(),
                address.getCountry(),
                address.getPostalCode(),
                customerAddressConverter.convertToCustomerAddress(address.getCustomer())
        );
    }

    public Set<AddressCustomerDto> convertToSetAddressCustomer(Set<Address> addresses){
        return addresses.stream().map(this::convertToAddressCustomer).collect(Collectors.toSet());
    }

}
