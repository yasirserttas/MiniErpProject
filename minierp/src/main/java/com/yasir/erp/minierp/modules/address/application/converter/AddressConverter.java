package com.yasir.erp.minierp.modules.address.application.converter;


import com.yasir.erp.minierp.modules.customer.application.converter.CustomerAddressConverter;
import com.yasir.erp.minierp.modules.supplier.application.converter.SupplierAddressConverter;
import com.yasir.erp.minierp.modules.user.application.converter.UserAddressConverter;
import com.yasir.erp.minierp.modules.address.application.dto.AddressDto;
import com.yasir.erp.minierp.modules.address.domain.model.Address;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AddressConverter {

    private final UserAddressConverter userAddressConverter;
    private final CustomerAddressConverter customerAddressConverter;
    private final SupplierAddressConverter supplierAddressConverter;

    public AddressConverter(UserAddressConverter userAddressConverter,
                            CustomerAddressConverter customerAddressConverter,
                            SupplierAddressConverter supplierAddressConverter) {
        this.userAddressConverter = userAddressConverter;
        this.customerAddressConverter = customerAddressConverter;
        this.supplierAddressConverter = supplierAddressConverter;
    }

    public AddressDto convertToAddress(Address address) {
        return new AddressDto(
                address.getId(),
                address.getStreet(),
                address.getDistrict(),
                address.getCity(),
                address.getCountry(),
                address.getPostalCode(),
                (address.getUser()     != null ? userAddressConverter.
                        convertToUserAddress(address.getUser())           : null),
                (address.getCustomer() != null ? customerAddressConverter.
                        convertToCustomerAddress(address.getCustomer()) : null),
                (address.getSupplier() != null ? supplierAddressConverter
                        .convertToSupplierAddress(address.getSupplier()) : null)
        );
    }

    public Set<AddressDto> convertToAddressSet(Set<Address> addresses) {
        return addresses.stream().map(this::convertToAddress).collect(Collectors.toSet());
    }
}
