package com.yasir.erp.minierp.modules.purchaseItem.application.converter;

import com.yasir.erp.minierp.modules.product.application.converter.ProductPurchaseItemConverter;
import com.yasir.erp.minierp.modules.purchaseItem.application.dto.PurchaseItemDto;
import com.yasir.erp.minierp.modules.purchaseItem.domain.model.PurchaseItem;
import org.springframework.stereotype.Component;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PurchaseOrderItemConverter {

    private final ProductPurchaseItemConverter productPurchaseItemConverter;

    public PurchaseOrderItemConverter(ProductPurchaseItemConverter productPurchaseItemConverter) {
        this.productPurchaseItemConverter = productPurchaseItemConverter;
    }

    public PurchaseItemDto convertToPurchaseOrderItemDto(PurchaseItem purchaseItem){

        return new PurchaseItemDto(
                purchaseItem.getId(),
                productPurchaseItemConverter.convertToProductPurchaseItemDto(purchaseItem.getProduct()),
                purchaseItem.getQuantity(),
                purchaseItem.getPurchasePrice(),
                purchaseItem.getTaxRate(),
                purchaseItem.getTaxAmount(),
                purchaseItem.getTotalCost()
        );
    }

    public Set<PurchaseItemDto> convertToPurchaseOrderItemDtoSet(Set<PurchaseItem> purchaseItems){

        return purchaseItems.stream().
                map(
                this::convertToPurchaseOrderItemDto)
                .collect(Collectors.toSet());
    }

}
