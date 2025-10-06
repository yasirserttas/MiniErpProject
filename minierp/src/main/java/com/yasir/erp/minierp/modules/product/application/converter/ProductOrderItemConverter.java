package com.yasir.erp.minierp.modules.product.application.converter;

import com.yasir.erp.minierp.modules.product.application.dto.ProductOrderItemDto;
import com.yasir.erp.minierp.modules.product.domain.model.Product;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProductOrderItemConverter {

    public ProductOrderItemDto convertToProductOrderItemDto(Product product) {
        return new ProductOrderItemDto(
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

    public Set<ProductOrderItemDto> convertToSetProductOrderItemDto(Set<Product> products) {
        return products.stream()
                .map(this::convertToProductOrderItemDto)
                .collect(Collectors.toSet());
    }
}
