package com.yasir.erp.minierp.modules.product.application.converter;

import com.yasir.erp.minierp.modules.product.application.dto.ProductUserDto;
import com.yasir.erp.minierp.modules.product.domain.model.Product;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProductUserConverter {

    public ProductUserDto convertToProductUserDto(Product product) {
        return new ProductUserDto(
                product.getId(),
                product.getPublicId(),
                product.getName(),
                product.getBrand(),
                product.getPrice(),
                product.getVatRate(),
                product.getActive()
        );
    }

    public Set<ProductUserDto> convertToSetProductUserDto(Set<Product> products) {
        return products.stream()
                .map(this::convertToProductUserDto)
                .collect(Collectors.toSet());
    }
}
