package com.yasir.erp.minierp.modules.supplier.application.converter;

import com.yasir.erp.minierp.modules.supplier.application.dto.SupplierPurchaseOrderDto;
import com.yasir.erp.minierp.modules.supplier.domain.model.Supplier;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SupplierPurchaseOrderConverter {

    public SupplierPurchaseOrderDto convertToSupplierPurchaseOrderDto(Supplier supplier) {
        return new SupplierPurchaseOrderDto(
                supplier.getId(),
                supplier.getName(),
                supplier.getTaxNumber(),
                supplier.getPhone(),
                supplier.getEmail(),
                supplier.getActive()
        );
    }

    public Set<SupplierPurchaseOrderDto> convertToSetSupplierPurchaseOrderDto(Set<Supplier> suppliers) {
        return suppliers.stream()
                .map(this::convertToSupplierPurchaseOrderDto)
                .collect(Collectors.toSet());
    }
}