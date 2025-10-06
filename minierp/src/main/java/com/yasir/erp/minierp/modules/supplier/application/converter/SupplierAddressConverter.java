package com.yasir.erp.minierp.modules.supplier.application.converter;

import com.yasir.erp.minierp.modules.supplier.application.dto.SupplierAddressDto;
import com.yasir.erp.minierp.modules.supplier.domain.model.Supplier;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SupplierAddressConverter {

    public SupplierAddressDto convertToSupplierAddress(Supplier supplier){
        return new SupplierAddressDto(
                supplier.getId(),
                supplier.getName()
        );
    }

    public Set<SupplierAddressDto> convertToSupplierAddress(Set<Supplier> suppliers){
        return suppliers.stream().map(this::convertToSupplierAddress).collect(Collectors.toSet());
    }
}
