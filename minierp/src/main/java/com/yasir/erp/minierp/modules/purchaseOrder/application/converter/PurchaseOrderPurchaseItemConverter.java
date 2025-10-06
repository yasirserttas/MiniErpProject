package com.yasir.erp.minierp.modules.purchaseOrder.application.converter;

import com.yasir.erp.minierp.modules.purchaseOrder.application.dto.PurchaseOrderPurchaseItemDto;
import com.yasir.erp.minierp.modules.purchaseOrder.domain.model.PurchaseOrder;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PurchaseOrderPurchaseItemConverter {

    public PurchaseOrderPurchaseItemDto convertToPurchaseOrderPurchaseItemDto
            (PurchaseOrder purchaseOrder){
        return new PurchaseOrderPurchaseItemDto(
                purchaseOrder.getId(),
                purchaseOrder.getStatus(),
                purchaseOrder.getCreatedAt(),
                purchaseOrder.getTotalAmount(),
                purchaseOrder.getActive()
        );
    }

    public Set<PurchaseOrderPurchaseItemDto> convertToSetPurchaseOrderPurchaseItemDto
            (Set<PurchaseOrder> purchaseOrders){
        return purchaseOrders.stream().
                map(this::convertToPurchaseOrderPurchaseItemDto).collect(Collectors.toSet());
    }

}
