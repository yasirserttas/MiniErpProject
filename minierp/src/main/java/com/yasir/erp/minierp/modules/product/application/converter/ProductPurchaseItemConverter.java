package com.yasir.erp.minierp.modules.product.application.converter;

import com.yasir.erp.minierp.modules.product.application.dto.ProductPurchaseItemDto;
import com.yasir.erp.minierp.modules.product.domain.model.Product;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProductPurchaseItemConverter {

    public ProductPurchaseItemDto convertToProductPurchaseItemDto(Product product) {
        return new ProductPurchaseItemDto(
                product.getId(),
                product.getPublicId(),
                product.getName(),
                product.getBrand(),
                product.getCategory(),
                product.getVatRate(),
                product.getImageUrl(),
                product.getActive()
        );
    }

    public Set<ProductPurchaseItemDto> convertToSetProductPurchaseItemDto(Set<Product> products) {
        return products.stream()
                .map(this::convertToProductPurchaseItemDto)
                .collect(Collectors.toSet());
    }
}
