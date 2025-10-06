package com.yasir.erp.minierp.modules.product.application.converter;

import com.yasir.erp.minierp.modules.product.application.dto.ProductStockDto;
import com.yasir.erp.minierp.modules.product.domain.model.Product;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProductStockConverter {

    public ProductStockDto convertToProductStockDto(Product product) {
        return new ProductStockDto(
                product.getId(),
                product.getPublicId(),
                product.getName(),
                product.getBrand(),
                product.getImageUrl(),
                product.getActive()
        );
    }

    public Set<ProductStockDto> convertToSetProductStockDto(Set<Product> products) {
        return products.stream()
                .map(this::convertToProductStockDto)
                .collect(Collectors.toSet());
    }
}
