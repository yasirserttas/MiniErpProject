package com.yasir.erp.minierp.modules.supplier.application.converter;


import com.yasir.erp.minierp.modules.address.application.converter.AddressSupplierConverter;
import com.yasir.erp.minierp.modules.purchaseOrder.application.converter.PurchaseOrderSupplierConverter;
import com.yasir.erp.minierp.modules.supplier.application.dto.SupplierDto;
import com.yasir.erp.minierp.modules.supplier.domain.model.Supplier;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SupplierConverter {


    private final AddressSupplierConverter addressSupplierConverter;
    private final PurchaseOrderSupplierConverter purchaseOrderSupplierConverter;

    public SupplierConverter(AddressSupplierConverter addressSupplierConverter,
                             PurchaseOrderSupplierConverter purchaseOrderSupplierConverter) {
        this.addressSupplierConverter = addressSupplierConverter;
        this.purchaseOrderSupplierConverter = purchaseOrderSupplierConverter;
    }

    public SupplierDto convertToSupplierDto(Supplier supplier){

        return new SupplierDto(
               supplier.getId(),
                supplier.getName(),
                supplier.getTaxNumber(),
                supplier.getPhone(),
                supplier.getEmail(),
                purchaseOrderSupplierConverter.
                        convertToSetPurchaseOrderSupplier(supplier.getPurchaseOrders()),
                addressSupplierConverter.convertToSetAddressSupplier(supplier.getAddresses()),
                supplier.getCreatedAt(),
                supplier.getUpdateAt(),
                supplier.getActive()
        );

    }

    public Set<SupplierDto> convertToSupplierSetDto(Set<Supplier> suppliers){

        return suppliers.stream()
                .map(this::convertToSupplierDto)
                .collect(Collectors.toSet());
    }


}
