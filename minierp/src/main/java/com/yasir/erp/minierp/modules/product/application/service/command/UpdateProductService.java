package com.yasir.erp.minierp.modules.product.application.service.command;

import com.yasir.erp.minierp.modules.product.application.converter.ProductConverter;
import com.yasir.erp.minierp.modules.product.application.dto.ProductDto;
import com.yasir.erp.minierp.modules.product.application.dto.request.UpdateProductDtoRequest;
import com.yasir.erp.minierp.modules.product.domain.model.Product;
import com.yasir.erp.minierp.modules.product.domain.port.inbound.command.UpdateProductUseCase;
import com.yasir.erp.minierp.modules.product.domain.port.outbound.command.ProductCommandPort;
import com.yasir.erp.minierp.modules.product.domain.port.outbound.query.ProductQueryPort;
import com.yasir.erp.minierp.modules.product.application.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UpdateProductService implements UpdateProductUseCase {

    private final ProductCommandPort commandPort;
    private final ProductQueryPort queryPort;
    private final ProductConverter converter;

    public UpdateProductService(ProductCommandPort commandPort,
                                ProductQueryPort queryPort,
                                ProductConverter converter) {
        this.commandPort = commandPort;
        this.queryPort = queryPort;
        this.converter = converter;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ProductDto updateProduct(UpdateProductDtoRequest updateProductDtoRequest) {
        Product existing = queryPort.
                findByIdAndActive(updateProductDtoRequest.getId(), true)
                .orElseThrow(() -> new
                        ProductNotFoundException(updateProductDtoRequest.getId()));

        Product updated = new Product(
                existing.getId(),
                existing.getPublicId(),
                existing.getUser(),
                updateProductDtoRequest.getName(),
                updateProductDtoRequest.getDescription(),
                updateProductDtoRequest.getPrice(),
                updateProductDtoRequest.getVatRate(),
                updateProductDtoRequest.getBrand(),
                updateProductDtoRequest.getCategory(),
                true,
                updateProductDtoRequest.getImageUrl(),
                existing.getCreateAt(),
                LocalDateTime.now()
        );

        return converter.convertToProductDto(commandPort.save(updated));
    }
}
