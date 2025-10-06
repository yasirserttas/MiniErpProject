package com.yasir.erp.minierp.modules.product.application.service.command;

import com.yasir.erp.minierp.modules.user.application.exception.UserNotFoundException;
import com.yasir.erp.minierp.modules.product.application.converter.ProductConverter;
import com.yasir.erp.minierp.modules.product.application.dto.ProductDto;
import com.yasir.erp.minierp.modules.product.application.dto.request.CreateProductDtoRequest;
import com.yasir.erp.minierp.modules.product.domain.model.Product;
import com.yasir.erp.minierp.modules.product.domain.port.inbound.command.AddProductUseCase;
import com.yasir.erp.minierp.modules.product.domain.port.outbound.command.ProductCommandPort;
import com.yasir.erp.minierp.modules.stock.application.converter.request.CreateStockDtoRequest;
import com.yasir.erp.minierp.modules.stock.domain.port.inbound.command.AddStockUseCase;
import com.yasir.erp.minierp.common.generator.ShortIdGenerator;
import com.yasir.erp.minierp.common.generator.UlidGenerator;
import com.yasir.erp.minierp.modules.user.domain.model.User;
import com.yasir.erp.minierp.modules.user.domain.port.outbound.query.UserByIdAndActiveQueryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AddProductService implements AddProductUseCase {

    private final ProductCommandPort commandPort;
    private final UserByIdAndActiveQueryPort userByIdAndActiveQueryPort;
    private final AddStockUseCase addStockUseCase;
    private final ProductConverter converter;

    public AddProductService(ProductCommandPort commandPort,
                             UserByIdAndActiveQueryPort userByIdAndActiveQueryPort,
                             AddStockUseCase addStockUseCase,
                             ProductConverter converter) {
        this.commandPort = commandPort;
        this.userByIdAndActiveQueryPort = userByIdAndActiveQueryPort;
        this.addStockUseCase = addStockUseCase;
        this.converter = converter;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ProductDto addProduct(CreateProductDtoRequest createProductDtoRequest) {
        User user = userByIdAndActiveQueryPort.
                findByIdAndActive(createProductDtoRequest.getUserId(),true)
                .orElseThrow(() ->
                        new UserNotFoundException(createProductDtoRequest.getUserId()));

        Product product = new Product(
                UlidGenerator.generate(),
                ShortIdGenerator.generate(10),
                user,
                createProductDtoRequest.getName(),
                createProductDtoRequest.getDescription(),
                createProductDtoRequest.getPrice(),
                createProductDtoRequest.getVatRate(),
                createProductDtoRequest.getBrand(),
                createProductDtoRequest.getCategory(),
                true,
                createProductDtoRequest.getImageUrl(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        Product persisted = commandPort.save(product);

        addStockUseCase.addStock(
                new CreateStockDtoRequest(persisted.getId(),
                        createProductDtoRequest.getQuantity()));

        return converter.convertToProductDto(persisted);
    }
}
