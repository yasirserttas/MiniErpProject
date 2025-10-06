package com.yasir.erp.minierp.modules.product.application.converter;

import com.yasir.erp.minierp.modules.user.application.converter.UserProductConverter;
import com.yasir.erp.minierp.modules.product.application.dto.ProductDto;
import com.yasir.erp.minierp.modules.product.domain.model.Product;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProductConverter {

    private final UserProductConverter userProductConverter;

    public ProductConverter(UserProductConverter userProductConverter) {
        this.userProductConverter = userProductConverter;
    }

    public ProductDto convertToProductDto(Product product) {

        return  new ProductDto(
               product.getId(),
                product.getPublicId(),
                userProductConverter.convertToUserProductDto(product.getUser()),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getVatRate(),
                product.getBrand(),
                product.getCategory(),
                product.getActive(),
                product.getImageUrl(),
                product.getCreateAt(),
                product.getUpdateAt()
        );

    }

    public Set<ProductDto> convertToProductDtoSet(Set<Product> products) {
        return products.stream()
                .map(this::convertToProductDto)
                .collect(Collectors.toSet());
    }
}
