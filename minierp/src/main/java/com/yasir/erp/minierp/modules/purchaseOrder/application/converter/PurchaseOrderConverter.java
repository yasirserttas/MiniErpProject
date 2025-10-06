package com.yasir.erp.minierp.modules.purchaseOrder.application.converter;

import com.yasir.erp.minierp.modules.purchaseItem.application.converter.PurchaseItemPurchaseOrderConverter;
import com.yasir.erp.minierp.modules.supplier.application.converter.SupplierPurchaseOrderConverter;
import com.yasir.erp.minierp.modules.purchaseOrder.application.dto.PurchaseOrderDto;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.model.PurchaseOrder;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PurchaseOrderConverter {

    private final SupplierPurchaseOrderConverter supplierPurchaseOrderConverter;
    private final PurchaseItemPurchaseOrderConverter purchaseItemPurchaseOrderConverter;

    public PurchaseOrderConverter(SupplierPurchaseOrderConverter
                                          supplierPurchaseOrderConverter,
                                  PurchaseItemPurchaseOrderConverter
                                          purchaseItemPurchaseOrderConverter) {
        this.supplierPurchaseOrderConverter = supplierPurchaseOrderConverter;
        this.purchaseItemPurchaseOrderConverter = purchaseItemPurchaseOrderConverter;
    }

    public PurchaseOrderDto convertToPurchaseOrderDto(PurchaseOrder purchaseOrder){

        return new PurchaseOrderDto(
                purchaseOrder.getId(),
                supplierPurchaseOrderConverter.
                        convertToSupplierPurchaseOrderDto(purchaseOrder.getSupplier()),
                purchaseItemPurchaseOrderConverter.
                        convertToSetPurchaseItemPurchaseOrderDto(purchaseOrder.getPurchaseItems()),
                purchaseOrder.getStatus(),
                purchaseOrder.getCreatedAt(),
                purchaseOrder.getUpdatedAt(),
                purchaseOrder.getActive(),
                purchaseOrder.getTotalAmount()
        );
    }

    public Set<PurchaseOrderDto> convertToPurchaseOrderSetDto(Set<PurchaseOrder> purchaseOrders){

        return purchaseOrders.stream()
                .map(this::convertToPurchaseOrderDto).collect(Collectors.toSet());
    }
}
