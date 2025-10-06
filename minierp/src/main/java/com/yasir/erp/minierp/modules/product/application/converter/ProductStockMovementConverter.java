package com.yasir.erp.minierp.modules.product.application.converter;

import com.yasir.erp.minierp.modules.product.application.dto.ProductStockMovementDto;
import com.yasir.erp.minierp.modules.product.domain.model.Product;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProductStockMovementConverter {

    public ProductStockMovementDto convertToProductStockMovementDto(Product product) {
        return new ProductStockMovementDto(
                product.getId(),
                product.getPublicId(),
                product.getName(),
                product.getBrand(),
                product.getImageUrl(),
                product.getActive()
        );
    }

    public Set<ProductStockMovementDto> convertToSetProductStockMovementDto(Set<Product> products) {
        return products.stream()
                .map(this::convertToProductStockMovementDto)
                .collect(Collectors.toSet());
    }
}
