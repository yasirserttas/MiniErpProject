package com.yasir.erp.minierp.modules.purchaseOrder.application.converter;

import com.yasir.erp.minierp.modules.purchaseItem.application.converter.PurchaseItemPurchaseOrderConverter;
import com.yasir.erp.minierp.modules.purchaseOrder.application.dto.PurchaseOrderSupplierDto;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.model.PurchaseOrder;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PurchaseOrderSupplierConverter {

    private final PurchaseItemPurchaseOrderConverter purchaseItemPurchaseOrderConverter;

    public PurchaseOrderSupplierConverter
            (PurchaseItemPurchaseOrderConverter purchaseItemPurchaseOrderConverter) {
        this.purchaseItemPurchaseOrderConverter = purchaseItemPurchaseOrderConverter;
    }

    public PurchaseOrderSupplierDto convertToPurchaseOrderSupplier
            (PurchaseOrder purchaseOrder){
        return new PurchaseOrderSupplierDto(
                purchaseOrder.getId(),
                purchaseItemPurchaseOrderConverter.
                        convertToSetPurchaseItemPurchaseOrderDto(purchaseOrder.getPurchaseItems()),
                purchaseOrder.getStatus(),
                purchaseOrder.getActive(),
                purchaseOrder.getTotalAmount()
        );
    }

    public Set<PurchaseOrderSupplierDto> convertToSetPurchaseOrderSupplier
            (Set<PurchaseOrder> purchaseOrders){

        return purchaseOrders.stream().
                map(this::convertToPurchaseOrderSupplier).collect(Collectors.toSet());
    }

}

