package com.yasir.erp.minierp.modules.address.application.converter;

import com.yasir.erp.minierp.modules.supplier.application.converter.SupplierAddressConverter;
import com.yasir.erp.minierp.modules.address.application.dto.AddressSupplierDto;
import com.yasir.erp.minierp.modules.address.domain.model.Address;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AddressSupplierConverter {

    private final SupplierAddressConverter supplierAddressConverter;

    public AddressSupplierConverter(SupplierAddressConverter supplierAddressConverter) {
        this.supplierAddressConverter = supplierAddressConverter;
    }

    public AddressSupplierDto convertToAddressSupplier(Address address){
        return new AddressSupplierDto(
                address.getId(),
                address.getStreet(),
                address.getDistrict(),
                address.getCity(),
                address.getCountry(),
                address.getPostalCode(),
                supplierAddressConverter.convertToSupplierAddress(address.getSupplier())
        );
    }

    public Set<AddressSupplierDto> convertToSetAddressSupplier(Set<Address> addresses){
        return addresses.stream().
                map(this::convertToAddressSupplier).collect(Collectors.toSet());
    }
}
