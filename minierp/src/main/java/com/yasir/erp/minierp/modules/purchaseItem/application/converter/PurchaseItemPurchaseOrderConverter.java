package com.yasir.erp.minierp.modules.purchaseItem.application.converter;

import com.yasir.erp.minierp.modules.product.application.converter.ProductPurchaseItemConverter;
import com.yasir.erp.minierp.modules.purchaseItem.application.dto.PurchaseItemPurchaseOrderDto;
import com.yasir.erp.minierp.modules.purchaseItem.domain.model.PurchaseItem;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PurchaseItemPurchaseOrderConverter {

    private final ProductPurchaseItemConverter productPurchaseItemConverter;

    public PurchaseItemPurchaseOrderConverter
            (ProductPurchaseItemConverter productPurchaseItemConverter) {
        this.productPurchaseItemConverter = productPurchaseItemConverter;
    }

    public PurchaseItemPurchaseOrderDto convertToPurchaseItemPurchaseOrderDto
            (PurchaseItem purchaseItem){

        return new PurchaseItemPurchaseOrderDto(
                purchaseItem.getId(),
                productPurchaseItemConverter.
                        convertToProductPurchaseItemDto(purchaseItem.getProduct()),
                purchaseItem.getQuantity(),
                purchaseItem.getPurchasePrice(),
                purchaseItem.getTaxRate(),
                purchaseItem.getTaxAmount(),
                purchaseItem.getTotalCost()
        );
    }

    public Set<PurchaseItemPurchaseOrderDto> convertToSetPurchaseItemPurchaseOrderDto
            (Set<PurchaseItem> purchaseItems){
        return purchaseItems.stream().
                map(this::convertToPurchaseItemPurchaseOrderDto).collect(Collectors.toSet());
    }

}
